package com.example.eventshub.presentation.events

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun EventDetailScreen(
    eventId: Long,
    navController: NavHostController
) {
    // Dummy data for services
    val services = listOf("Catering", "Photography", "Decoration")

    Column(Modifier.padding(16.dp)) {
        Text("Event ID: $eventId", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("Services Registered:", style = MaterialTheme.typography.titleMedium)
        services.forEach {
            Text("• $it", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigateUp() }) {
            Text("Back")
        }
    }
}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun EventDetailScreen(event: Event, onBack: () -> Unit) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Event Details") },
//                navigationIcon = {
//                    IconButton(onClick = onBack) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = Color.White,
//                    titleContentColor = Color.Black
//                )
//            )
//        },
//        containerColor = Color.White
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .verticalScroll(rememberScrollState())
//                .padding(16.dp)
//        ) {
//            Card(
//                modifier = Modifier.fillMaxWidth(),
//                elevation = CardDefaults.cardElevation(4.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.White)
//            ) {
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = event.name,
//                        style = MaterialTheme.typography.headlineSmall,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    DetailRow(Icons.Default.DateRange, event.date)
//                    DetailRow(Icons.Default.LocationOn, "Event Place")
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    BudgetSection(
//                        spent = 15000,
//                        total = 20000
//                    )
//
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    ButtonFullWidth(text = "Edit Event") {
//                        //Edit onClick action
//                    }
//                }
//            }
//
//            // Additional sections can be added here
//        }
//    }
//}
//
//@Composable
//fun DetailRow(icon: ImageVector, text: String) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null,
//            tint = MaterialTheme.colorScheme.primary
//        )
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(text, style = MaterialTheme.typography.bodyMedium)
//    }
//}
//
//@Composable
//fun BudgetSection(spent: Int, total: Int) {
//    Column {
//        Text(
//            text = "Budget Overview",
//            style = MaterialTheme.typography.titleMedium,
//            fontWeight = FontWeight.Medium
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        LinearProgressIndicator(
//            progress = { spent / total.toFloat() },
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(8.dp),
//            color = MaterialTheme.colorScheme.primary,
//            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text("Spent: $$spent")
//            //Text("Remaining: $${total - spent}")
//            Text("Total: $$total")
//        }
//    }
//}