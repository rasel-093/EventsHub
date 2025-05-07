package com.example.eventshub.presentation.events.createevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.eventshub.R
import com.example.eventshub.data.model.Event
import com.example.eventshub.presentation.image.ImageUploadSection
import com.example.eventshub.presentation.image.ImageUploadViewModel
import com.example.eventshub.ui.theme.primaryColor
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CreateOrEditEventDialog(
    initial: Event? = null,
    onDismiss: () -> Unit,
    onConfirm: (Event) -> Unit
) {
    val surfaceColor = MaterialTheme.colorScheme.surface
    val isEdit = initial != null
    val context = LocalContext.current
    val viewModel: ImageUploadViewModel = koinViewModel()

    var name by remember { mutableStateOf(initial?.name ?: "") }
    var description by remember { mutableStateOf(initial?.description ?: "") }
    var budget by remember { mutableStateOf(initial?.budget?.toString() ?: "") }
    var selectedFile by remember { mutableStateOf<File?>(null) }
    var selectedDateTime by remember {
        mutableStateOf<LocalDateTime?>(initial?.date?.let {
            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime()
        })
    }

    val dateTimeText = selectedDateTime?.format(
        DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a")
    ) ?: "Select date and time"

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        containerColor = surfaceColor,
        tonalElevation = 8.dp,
        title = {
            Text(
                text = if (isEdit) "Edit Event" else "Create Event",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        },
        text = {
            Column(
                modifier = Modifier.padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Event Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                OutlinedTextField(
                    value = budget,
                    onValueChange = { budget = it },
                    label = { Text("Budget") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    prefix = { Text("$") }
                )

                // Replace Image Link input with image picker
                ImageUploadSection { file ->
                    selectedFile = file
                }

                Button(
                    onClick = {
                        val now = LocalDateTime.now()
                        val datePicker = DatePickerDialog(
                            context,
                            { _, year, month, day ->
                                val pickedDate = LocalDate.of(year, month + 1, day)
                                val timePicker = TimePickerDialog(
                                    context,
                                    { _, hour, minute ->
                                        val pickedTime = LocalTime.of(hour, minute)
                                        val dateTime = LocalDateTime.of(pickedDate, pickedTime)
                                        if (dateTime.isBefore(now)) return@TimePickerDialog
                                        selectedDateTime = dateTime
                                    },
                                    now.hour, now.minute, false
                                )
                                timePicker.show()
                            },
                            now.year, now.monthValue - 1, now.dayOfMonth
                        )
                        datePicker.show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    )
                ) {
                    Icon(painterResource(R.drawable.event64), null, tint = Color.White)
                    Spacer(Modifier.width(8.dp))
                    Text(dateTimeText)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isBlank() || description.isBlank() || budget.isBlank() || selectedDateTime == null) return@Button

                    val proceedWithConfirm: (String) -> Unit = { imageUrl ->
                        onConfirm(
                            Event(
                                id = initial?.id ?: 0L,
                                name = name,
                                description = description,
                                organizerId = initial?.organizerId ?: 0L,
                                imageLink = imageUrl,
                                date = selectedDateTime!!
                                    .atZone(ZoneId.systemDefault())
                                    .toInstant().toEpochMilli(),
                                budget = budget.toFloat()
                            )
                        )
                    }

                    if (selectedFile != null) {
                        viewModel.uploadImage(selectedFile!!) { response ->
                            if (response != null) {
                                proceedWithConfirm(response.imageLink)
                            } else {
                                Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        proceedWithConfirm(initial?.imageLink ?: "")
                    }
                },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(if (isEdit) "Update" else "Create")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

