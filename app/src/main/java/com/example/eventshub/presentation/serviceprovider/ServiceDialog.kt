package com.example.eventshub.presentation.serviceprovider

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.eventshub.data.model.Service
import com.example.eventshub.data.model.ServiceType

//@Composable
//fun CreateServiceDialog(
//    onDismiss: () -> Unit,
//    onConfirm: (Service) -> Unit
//) {
//    var title by remember { mutableStateOf("") }
//    var description by remember { mutableStateOf("") }
//    var fee by remember { mutableStateOf("") }
//    var imageLink by remember { mutableStateOf("") }
//    var selectedType by remember { mutableStateOf(ServiceType.CATERER) }
//
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        title = { Text("Create Service") },
//        text = {
//            Column {
//                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
//                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
//                OutlinedTextField(value = fee, onValueChange = { fee = it }, label = { Text("Fee") },
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                )
//                OutlinedTextField(value = imageLink, onValueChange = { imageLink = it }, label = { Text("Image Link") })
//
//                Spacer(Modifier.height(8.dp))
//
//                Text("Service Type")
//                Row {
//                    RadioButton(
//                        selected = selectedType == ServiceType.CATERER,
//                        onClick = { selectedType = ServiceType.CATERER }
//                    )
//                    Text("Caterer", modifier = Modifier.padding(end = 16.dp))
//                    RadioButton(
//                        selected = selectedType == ServiceType.DECORATOR,
//                        onClick = { selectedType = ServiceType.DECORATOR }
//                    )
//                    Text("Decorator")
//                }
//            }
//        },
//        confirmButton = {
//            TextButton(onClick = {1
//                if (title.isNotBlank() && description.isNotBlank() && fee.isNotBlank()) {
//                    onConfirm(
//                        Service(
//                            title = title,
//                            description = description,
//                            fee = fee.toDouble(),
//                            imageLink = imageLink,
//                            rating = 0f,
//                            serviceProviderId = 0L, // Will be overwritten
//                            serviceType = selectedType.toString(),
//                            id = 0
//                        )
//                    )
//                }
//            }) {
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
fun CreateOrEditServiceDialog(
    initial: Service? = null,
    onDismiss: () -> Unit,
    onConfirm: (Service) -> Unit
) {
    val isEdit = initial != null

    var title by remember { mutableStateOf(initial?.title ?: "") }
    var description by remember { mutableStateOf(initial?.description ?: "") }
    var fee by remember { mutableStateOf(initial?.fee?.toString() ?: "") }
    var imageLink by remember { mutableStateOf(initial?.imageLink ?: "") }
    var selectedType by remember { mutableStateOf(initial?.serviceType ?: ServiceType.CATERER) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEdit) "Edit Service" else "Create Service") },
        text = {
            Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
                OutlinedTextField(value = fee, onValueChange = { fee = it }, label = { Text("Fee") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                OutlinedTextField(value = imageLink, onValueChange = { imageLink = it }, label = { Text("Image Link") })

                Spacer(Modifier.height(8.dp))

                Text("Service Type")
                Row {
                    RadioButton(
                        selected = selectedType == ServiceType.CATERER,
                        onClick = { selectedType = ServiceType.CATERER }
                    )
                    Text("Caterer", modifier = Modifier.padding(end = 16.dp))
                    RadioButton(
                        selected = selectedType == ServiceType.DECORATOR,
                        onClick = { selectedType = ServiceType.DECORATOR }
                    )
                    Text("Decorator")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (title.isNotBlank() && description.isNotBlank() && fee.isNotBlank()) {
                    val base = Service(
                        id = initial?.id ?: 0L,
                        title = title,
                        description = description,
                        fee = fee.toDouble(),
                        imageLink = imageLink,
                        rating = initial?.rating ?: 0f,
                        serviceProviderId = initial?.serviceProviderId ?: 0L,
                        serviceType = selectedType.toString()
                    )
                    onConfirm(base)
                }
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

