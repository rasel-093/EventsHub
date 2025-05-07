package com.example.eventshub.presentation.image

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.eventshub.ui.theme.primaryColor
import java.io.File

@Composable
fun ImageUploadSection(
    onFileSelected: (File?) -> Unit
) {
    val context = LocalContext.current
    var selectedFile by remember { mutableStateOf<File?>(null) }
    var fileName by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")
            inputStream?.use { input ->
                file.outputStream().use { output -> input.copyTo(output) }
            }
            selectedFile = file
            fileName = file.name
            onFileSelected(file)
        }
    }

    Column {
        Button(
            onClick = {
                launcher.launch("image/*")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("Choose Image")
        }
        Spacer(modifier = Modifier.height(8.dp))
        fileName?.let {
            Text("Selected: $it", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
