package com.example.eventshub.presentation.home.detail
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eventshub.R
import com.example.eventshub.ui.theme.primaryColor
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun RatingSection(
    serviceId: Long,
    viewModel: ServiceDetailViewModel = koinViewModel(),
) {
    val rating = viewModel.rating
    val feedback = viewModel.feedback
    val isLoading = viewModel.isRatingLoading
    val snackbarMessage = viewModel.snackbarMessage

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Rate this service", style = MaterialTheme.typography.titleMedium)

        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            (1..5).forEach { star ->
                Icon(
                    painter = painterResource(if (star <= rating) R.drawable.star_filled64 else R.drawable.star_outlined64),
                    contentDescription = "Star $star",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { viewModel.onRatingChange(star) }
                        .padding(4.dp)
                )
            }
        }

//        OutlinedTextField(
//            value = feedback,
//            onValueChange = viewModel::onFeedbackChange,
//            label = { Text("Your feedback") },
//            modifier = Modifier.fillMaxWidth()
//        )

//        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                viewModel.submitRating(serviceId)
            },
            enabled = !isLoading && rating > 0,
            modifier = Modifier.align(Alignment.End),
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = Color.White
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text("Submit")
        }

        // Show Snackbar or Message
        snackbarMessage?.let { message ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                color = if (message.contains("Failed")) Color.Red else Color.Green,
                style = MaterialTheme.typography.bodySmall
            )
            // Clear it after display delay
            LaunchedEffect(message) {
                delay(3000)
                viewModel.clearSnackbar()
            }
        }
    }
}
