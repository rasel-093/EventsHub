package com.example.eventshub.presentation.serviceprovider

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.eventshub.data.model.Service
import com.example.eventshub.data.model.ServiceType
import com.example.eventshub.presentation.image.ImageUploadSection
import com.example.eventshub.presentation.image.ImageUploadViewModel
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun CreateOrEditServiceDialog(
    initial: Service? = null,
    onDismiss: () -> Unit,
    onConfirm: (Service) -> Unit,
    viewModel: ImageUploadViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val isEdit = initial != null

    var title by remember { mutableStateOf(initial?.title ?: "") }
    var description by remember { mutableStateOf(initial?.description ?: "") }
    var fee by remember { mutableStateOf(initial?.fee?.toString() ?: "") }
    var selectedType by remember { mutableStateOf(initial?.serviceType ?: ServiceType.CATERER) }

    // Selected image file
    var selectedImageFile by remember { mutableStateOf<File?>(null) }

    // State for uploaded image URL
    var uploadedImageUrl by remember { mutableStateOf(initial?.imageLink ?: "") }

    // Loading state for image upload
    var isUploading by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (isEdit) "Edit Service" else "Create Service") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") })
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") })
                OutlinedTextField(
                    value = fee,
                    onValueChange = { fee = it },
                    label = { Text("Fee") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(Modifier.height(8.dp))
                // 👇 Image upload UI
                ImageUploadSection { file ->
                    selectedImageFile = file
                }
                Spacer(modifier = Modifier.height(12.dp))
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
            TextButton(
                onClick = {
                    if (title.isNotBlank() && description.isNotBlank() && fee.isNotBlank()) {
                        if (selectedImageFile != null) {
                            // Image selected → upload first, then proceed
                            isUploading = true
                            viewModel.uploadImage(selectedImageFile!!) { response ->
                                isUploading = false
                                if (response != null) {
                                    uploadedImageUrl = response.imageLink
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Image upload failed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                // Proceed with confirm regardless of upload result
                                val service = Service(
                                    id = initial?.id ?: 0L,
                                    title = title,
                                    description = description,
                                    fee = fee.toDouble(),
                                    imageLink = uploadedImageUrl,
                                    rating = initial?.rating ?: 0f,
                                    serviceProviderId = initial?.serviceProviderId ?: 0L,
                                    serviceType = selectedType.toString()
                                )
                                onConfirm(service)
                            }
                        } else {
                            // No image selected → create/update immediately
                            val service = Service(
                                id = initial?.id ?: 0L,
                                title = title,
                                description = description,
                                fee = fee.toDouble(),
                                imageLink = uploadedImageUrl, // could be existing or empty
                                rating = initial?.rating ?: 0f,
                                serviceProviderId = initial?.serviceProviderId ?: 0L,
                                serviceType = selectedType.toString()
                            )
                            onConfirm(service)
                        }
                    }
                },
                enabled = !isUploading
            ) {
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

