package com.example.eventshub.presentation.home.detail

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.eventshub.presentation.home.shared.SharedServiceViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServiceDetailScreen(serviceId: Long, sharedViewModel: SharedServiceViewModel = koinViewModel()) {
    var showSelection by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<String?>(null) }
    val options = listOf("Option A", "Option B", "Option C")
    val service = sharedViewModel.getServiceById(serviceId)

    Column(modifier = Modifier.padding(16.dp)) {
        Text(service!!.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text(service.description)
        Spacer(Modifier.height(8.dp))
        Text("Rating: ${service.rating}")
        Text("Fee: $${service.fee}")
        Text("Type: ${service.serviceType}")
        Spacer(Modifier.height(16.dp))

        Button(onClick = { showSelection = true }) {
            Text("Add")
        }
        if (showSelection) {
            options.forEach { item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .clickable { selectedItem = item }
                        .background(if (selectedItem == item) Color.LightGray else Color.Transparent)
                        .padding(8.dp)
                ) {
                    Text(item)
                }
            }

            selectedItem?.let {
                Button(onClick = {
                    // Perform your future operation here
                    showSelection = false
                }) {
                    Text("Confirm")
                }
            }
        }
    }
}
