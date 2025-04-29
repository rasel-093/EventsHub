package com.example.eventshub.presentation.events.createevent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.SharedPreferences
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//@Composable
//fun CreateEventDialog(
//    onDismiss: () -> Unit,
//    onConfirm: (Event) -> Unit
//) {
//    var name by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var budget by remember { mutableStateOf("") }
//    var imageLink by remember { mutableStateOf("") }
//
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = { Text("Create New Event") },
//        text = {
//            Column {
//                OutlinedTextField(
//                    value = name,
//                    onValueChange = { name = it },
//                    label = { Text("Event Name") }
//                )
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(
//                    value = description,
//                    onValueChange = { description = it },
//                    label = { Text("Description") }
//                )
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(
//                    value = budget,
//                    onValueChange = { budget = it },
//                    label = { Text("Budget") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                )
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(
//                    value = imageLink,
//                    onValueChange = { imageLink = it },
//                    label = { Text("Image Link (Optional)") }
//                )
//            }
//        },
//        confirmButton = {
//            TextButton(
//                onClick = {
//                    if (name.isNotBlank() && description.isNotBlank() && budget.isNotBlank()) {
//                        onConfirm(
//                            Event(
//                                name = name,
//                                description = description,
//                                organizerId = 0, // You will set real organizerId
//                                imageLink = imageLink,
//                                date = System.currentTimeMillis(), // Current time for now
//                                budget = budget.toFloat()
//                            )
//                        )
//                    }
//                }
//            ) {
//                Text("Create")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel")
//            }
//        }
//    )
//}
//@Composable
//fun CreateEventDialog(
//    onDismiss: () -> Unit,
//    onConfirm: (Event) -> Unit
//) {
//    val context = LocalContext.current
//
//    var name by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var budget by remember { mutableStateOf("") }
//    var imageLink by remember { mutableStateOf("") }
//
//    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
//    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }
//
//    val datePickerDialog = remember {
//        DatePickerDialog(
//            context,
//            { _, year, month, dayOfMonth ->
//                selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
//            },
//            LocalDate.now().year,
//            LocalDate.now().monthValue - 1,
//            LocalDate.now().dayOfMonth
//        )
//    }
//
//    val timePickerDialog = remember {
//        TimePickerDialog(
//            context,
//            { _, hourOfDay, minute ->
//                selectedTime = LocalTime.of(hourOfDay, minute)
//            },
//            LocalTime.now().hour,
//            LocalTime.now().minute,
//            false
//        )
//    }
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = { Text("Create New Event") },
//        text = {
//            Column {
//                OutlinedTextField(
//                    value = name,
//                    onValueChange = { name = it },
//                    label = { Text("Event Name") }
//                )
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(
//                    value = description,
//                    onValueChange = { description = it },
//                    label = { Text("Description") }
//                )
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(
//                    value = budget,
//                    onValueChange = { budget = it },
//                    label = { Text("Budget") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                )
//                Spacer(Modifier.height(8.dp))
//                OutlinedTextField(
//                    value = imageLink,
//                    onValueChange = { imageLink = it },
//                    label = { Text("Image Link (Optional)") }
//                )
//                Spacer(Modifier.height(12.dp))
//
//                // Date Picker Button
//                Button(onClick = { datePickerDialog.show() }) {
//                    Text(text = selectedDate?.toString() ?: "Pick Date")
//                }
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Time Picker Button
//                Button(onClick = { timePickerDialog.show() }) {
//                    Text(text = selectedTime?.toString() ?: "Pick Time")
//                }
//
//                if (selectedDate != null && selectedTime != null) {
//                    val formatted = "${selectedDate.toString()} ${selectedTime.toString()}"
//                    Text("Selected: $formatted", fontSize = 13.sp, color = Color.Gray)
//                }
//            }
//        },
//        confirmButton = {
//            TextButton(
//                onClick = {
//                    if (name.isNotBlank() && description.isNotBlank() && budget.isNotBlank()
//                        && selectedDate != null && selectedTime != null
//                    ) {
//                        // ✅ Combine date + time → millis
//                        val dateTime = LocalDateTime.of(selectedDate, selectedTime)
//                        val millis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
//
//                        onConfirm(
//                            Event(
//                                name = name,
//                                description = description,
//                                organizerId = 0, // Set real one
//                                imageLink = imageLink,
//                                date = millis,
//                                budget = budget.toFloat()
//                            )
//                        )
//                    }
//                }
//            ) {
//                Text("Create")
//            }
//        },
//        dismissButton = {
//            TextButton(onClick = onDismiss) {
//                Text("Cancel")
//            }
//        }
//    )
//}

@Composable
fun CreateEventDialog(
    onDismiss: () -> Unit,
    onConfirm: (Event) -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var imageLink by remember { mutableStateOf("") }

    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedTime by remember { mutableStateOf<LocalTime?>(null) }

    var dateError by remember { mutableStateOf<String?>(null) }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val picked = LocalDate.of(year, month + 1, dayOfMonth)
                if (picked.isBefore(LocalDate.now())) {
                    dateError = "Cannot select a past date"
                } else {
                    selectedDate = picked
                    dateError = null
                }
            },
            LocalDate.now().year,
            LocalDate.now().monthValue - 1,
            LocalDate.now().dayOfMonth
        )
    }

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                selectedTime = LocalTime.of(hourOfDay, minute)
            },
            LocalTime.now().hour,
            LocalTime.now().minute,
            false
        )
    }

    val prettyDateTime = remember(selectedDate, selectedTime) {
        if (selectedDate != null && selectedTime != null) {
            val dateTime = LocalDateTime.of(selectedDate, selectedTime)
            val formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy 'at' h:mm a")
            dateTime.format(formatter)
        } else ""
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Create New Event") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Event Name") }
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") }
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = budget,
                    onValueChange = { budget = it },
                    label = { Text("Budget") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = imageLink,
                    onValueChange = { imageLink = it },
                    label = { Text("Image Link (Optional)") }
                )

                Spacer(Modifier.height(12.dp))

                // Date Picker Button
                Button(onClick = { datePickerDialog.show() }) {
                    Text(text = selectedDate?.toString() ?: "Pick Date")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Time Picker Button
                Button(onClick = { timePickerDialog.show() }) {
                    Text(text = selectedTime?.toString() ?: "Pick Time")
                }

                if (prettyDateTime.isNotBlank()) {
                    Text("Selected: $prettyDateTime", fontSize = 13.sp, color = Color.Gray)
                }

                if (dateError != null) {
                    Text(dateError!!, color = Color.Red, fontSize = 12.sp)
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    var hasError = false
                    if (selectedDate == null || selectedTime == null) {
                        dateError = "Please select both date and time"
                        hasError = true
                    }

                    if (!hasError && name.isNotBlank() && description.isNotBlank() && budget.isNotBlank()) {
                        val dateTime = LocalDateTime.of(selectedDate, selectedTime)
                        val millis = dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

                        onConfirm(
                            Event(
                                name = name,
                                description = description,
                                organizerId = 0,
                                imageLink = imageLink,
                                date = millis,
                                budget = budget.toFloat()
                            )
                        )
                    }
                }
            ) {
                Text("Create")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

