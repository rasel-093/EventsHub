package com.example.eventshub.presentation.events.eventdetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.eventshub.components.ButtonFullWidth
import com.example.eventshub.components.TopBarWithBackButton
import com.example.eventshub.data.model.BookingStatus
import com.example.eventshub.data.model.Event
import com.example.eventshub.data.model.ServiceBookingInfo
import com.example.eventshub.presentation.booking.BookingViewModel
import com.example.eventshub.presentation.events.EventsViewModel
import com.example.eventshub.presentation.events.createevent.CreateOrEditEventDialog
import com.example.eventshub.util.formatMillisToReadableDateTime
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(
    initialEvent: Event,
    navController: NavHostController,
    viewModel: EventDetailViewModel = koinViewModel(),
    eventViewModel: EventsViewModel = koinViewModel(),
    bookingViewModel: BookingViewModel = koinViewModel()
) {
    var event by remember { mutableStateOf(initialEvent) } // ✅ This makes it reactive
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    bookingViewModel.loadRegisteredServices(event.id)

    LaunchedEffect(Unit) {
        viewModel.loadRegisteredServices(event.id)
    }

    LaunchedEffect(viewModel.snackbarMessage) {
        viewModel.snackbarMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.clearMessage()
            }
        }
    }

    Scaffold(
        topBar = { TopBarWithBackButton(topBarText = event.name, {navController.navigateUp()}) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
               .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
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
                        showEditDialog = true
                    }
                    ButtonFullWidth(text = "Delete Event") {
                        showDeleteDialog = true
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Divider()

            Text("Cart", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            val services = viewModel.services.value

            if (services.isEmpty()) {
                Text("No services added to this event yet.", color = Color.Gray)
            } else {
                services.forEach {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(it.title, style = MaterialTheme.typography.titleMedium)
                                Text("Type: ${it.serviceType}", color = Color.Gray)
                                Text("Fee: $${it.fee}")
                            }
                            Button(onClick = {
                                val bookingInfo = ServiceBookingInfo(
                                    serviceId = it.id,
                                    eventOrganizerId = event.organizerId, // make sure this exists in your Event model
                                    status = BookingStatus.PENDING
                                )
                                bookingViewModel.confirmServiceBooking(bookingInfo)
                                bookingViewModel.loadRegisteredServices(event.id)
                            }) {
                                Text("Confirm")
                            }

                            IconButton(onClick = {
                                viewModel.removeServiceFromEvent(
                                    eventId = event.id,
                                    serviceId = it.id
                                )
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Remove service",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }

                        }
                    }
                }
            }

            //Registered Services
            Spacer(Modifier.height(24.dp))
            Text("Registered Services", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            val registered = bookingViewModel.registeredServices.value
            if (registered.isEmpty()) {
                Text("No registered services yet.", color = Color.Gray)
            } else {
                registered.forEach {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Text(it.title, style = MaterialTheme.typography.titleMedium)
                            Text("Type: ${it.serviceType}", color = Color.Gray)
                            Text("Fee: $${it.fee}")
                            Text("Status: ${it.bookingStatus}", color = Color.Blue)
                        }
                    }
                }
//                LazyColumn {
//                    items(registered) { booking ->
//
//                    }
//                }
            }


        }

        // ✅ Dialog and state update after success
        if (showEditDialog) {
            CreateOrEditEventDialog(
                initial = event,
                onDismiss = { showEditDialog = false },
                onConfirm = { updatedEvent ->
                    viewModel.updateEvent(updatedEvent)
                    event = updatedEvent // ✅ UI now reflects updated data
                    showEditDialog = false
                    eventViewModel.loadEvents()
                }
            )
        }
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text("Delete Event") },
                text = { Text("Are you sure you want to delete this event? This action cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = {
                        showDeleteDialog = false
                        viewModel.deleteEvent(event.id) {
                            navController.popBackStack()
                        }
                    }) { Text("Delete") }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
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