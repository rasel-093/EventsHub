package com.example.eventshub.presentation.events.createevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventshub.data.model.Event
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
        title = { Text(if (isEdit) "Edit Event" else "Create Event") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Event Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = budget,
                    onValueChange = { budget = it },
                    label = { Text("Budget") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = imageLink,
                    onValueChange = { imageLink = it },
                    label = { Text("Image Link (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))

                Button(onClick = {
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

                                    if (dateTime.isBefore(now)) {
                                        // Prevent past dates
                                        return@TimePickerDialog
                                    }

                                    selectedDateTime = dateTime
                                },
                                now.hour, now.minute, false
                            )
                            timePicker.show()
                        },
                        now.year, now.monthValue - 1, now.dayOfMonth
                    )
                    datePicker.show()
                }) {
                    Text(dateTimeText)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (name.isBlank() || description.isBlank() || budget.isBlank() || selectedDateTime == null) {
                    return@TextButton // Prevent submission with invalid input
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
            }) {
                Text(if (isEdit) "Update" else "Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


