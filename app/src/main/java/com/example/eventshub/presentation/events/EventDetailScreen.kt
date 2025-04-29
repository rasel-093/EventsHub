package com.example.eventshub.presentation.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.eventshub.components.ButtonFullWidth
import com.example.eventshub.data.model.Event
import com.example.eventshub.util.formatMillisToReadableDateTime

//@Composable
//fun EventDetailScreen(
//    event: Event,
//    navController: NavHostController
//) {
//    // Dummy data for services
//    val services = listOf("Catering", "Photography", "Decoration")
//
//    Column(Modifier.padding(16.dp)) {
//        Text("Event ID: ${event.name}", style = MaterialTheme.typography.headlineSmall)
//        Spacer(Modifier.height(8.dp))
//        Text("Services Registered:", style = MaterialTheme.typography.titleMedium)
//        services.forEach {
//            Text("• $it", style = MaterialTheme.typography.bodyLarge)
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Button(onClick = { navController.navigateUp() }) {
//            Text("Back")
//        }
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(event: Event, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Event Details") },
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = event.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = event.description,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DetailRow(Icons.Default.DateRange, formatMillisToReadableDateTime(event.date))

                    Spacer(modifier = Modifier.height(16.dp))

                    BudgetSection(
                        spent = 0f,
                        total = event.budget
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    ButtonFullWidth(text = "Edit Event") {
                        //Edit onClick action
                    }
                }
            }

            // Additional sections can be added here
        }
    }
}

@Composable
fun DetailRow(icon: ImageVector, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun BudgetSection(spent: Float, total: Float) {
    Column {
        Text(
            text = "Budget Overview",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { spent / total.toFloat() },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Spent: $$spent")
            //Text("Remaining: $${total - spent}")
            Text("Budget: $$total")
        }
    }
}