package com.example.eventshub.presentation.events.createevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.TextFieldDefaults
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
import com.example.eventshub.ui.theme.primaryColor
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
    //val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surface
    val isEdit = initial != null

    var name by remember { mutableStateOf(initial?.name ?: "") }
    var description by remember { mutableStateOf(initial?.description ?: "") }
    var budget by remember { mutableStateOf(initial?.budget?.toString() ?: "") }
    var imageLink by remember { mutableStateOf(initial?.imageLink ?: "") }

    var selectedDateTime by remember { mutableStateOf<LocalDateTime?>(initial?.date?.let {
        Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }) }

    val context = LocalContext.current
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
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = primaryColor,
                        focusedIndicatorColor = primaryColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = primaryColor,
                        focusedIndicatorColor = primaryColor
                    ),
                    shape = RoundedCornerShape(8.dp),
                    maxLines = 3
                )

                OutlinedTextField(
                    value = budget,
                    onValueChange = { budget = it },
                    label = { Text("Budget") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = primaryColor,
                        focusedIndicatorColor = primaryColor
                    ),
                    shape = RoundedCornerShape(8.dp),
                    prefix = { Text("$") }
                )

                OutlinedTextField(
                    value = imageLink,
                    onValueChange = { imageLink = it },
                    label = { Text("Image Link (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = primaryColor,
                        focusedIndicatorColor = primaryColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

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
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor.copy(alpha = 0.1f),
                        contentColor = primaryColor
                    ),
                    border = BorderStroke(1.dp, primaryColor.copy(alpha = 0.3f))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.event64),
                        contentDescription = null,
                        tint = primaryColor
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(dateTimeText)
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isBlank() || description.isBlank() || budget.isBlank() || selectedDateTime == null) {
                        return@Button
                    }
                    onConfirm(
                        Event(
                            id = initial?.id ?: 0L,
                            name = name,
                            description = description,
                            organizerId = initial?.organizerId ?: 0L,
                            imageLink = imageLink,
                            date = selectedDateTime!!.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                            budget = budget.toFloat()
                        )
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor,
                    contentColor = Color.White
                )
            ) {
                Text(if (isEdit) "Update" else "Create")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text("Cancel")
            }
        }
    )
}
