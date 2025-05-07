package com.example.eventshub.presentation.home.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eventshub.R

@Composable
fun RatingBarWithFeedback(
    onSubmit: (rating: Int, feedback: String) -> Unit
) {
    var rating by remember { mutableStateOf(0) }
    var feedback by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Rate this service", style = MaterialTheme.typography.titleMedium)

        Row {
            (1..5).forEach { star ->
                Icon(
                    painter = painterResource(
                        if (star <= rating) R.drawable.star_filled64 else R.drawable.star_outlined64
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { rating = star },
                    tint = Color(0xFFFFC107)
                )
            }
        }

        OutlinedTextField(
            value = feedback,
            onValueChange = { feedback = it },
            label = { Text("Feedback (optional)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Button(
            onClick = { onSubmit(rating, feedback) },
            enabled = rating > 0,
            modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
        ) {
            Text("Submit")
        }
    }
}
