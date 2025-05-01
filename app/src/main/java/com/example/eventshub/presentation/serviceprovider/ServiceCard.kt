package com.example.eventshub.presentation.serviceprovider

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventshub.data.model.Service

@Composable
fun ServiceCard(
    service: Service,
    onDelete: (Long) -> Unit,
    onEdit: (Service) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(service.title, style = MaterialTheme.typography.titleMedium)
            Text("Type: ${service.serviceType}", color = Color.Gray)
            Text("Fee: \$${service.fee}", color = Color.Gray)

            Spacer(Modifier.height(8.dp))
            Row {
                TextButton(onClick = { onEdit(service) }) {
                    Text("Edit")
                }
                TextButton(onClick = { onDelete(service.id) }) {
                    Text("Delete", color = Color.Red)
                }
            }
        }
    }
}

