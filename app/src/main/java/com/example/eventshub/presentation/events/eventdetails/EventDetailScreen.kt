package com.example.eventshub.presentation.events.eventdetails

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.eventshub.R
import com.example.eventshub.components.TopBarWithBackButton
import com.example.eventshub.data.model.Event
import com.example.eventshub.presentation.booking.BookingViewModel
import com.example.eventshub.presentation.events.EventsViewModel
import com.example.eventshub.presentation.events.components.BudgetSection
import com.example.eventshub.presentation.events.components.DetailRow
import com.example.eventshub.presentation.events.components.RegisteredServiceCard
import com.example.eventshub.presentation.events.components.ServiceCartCard
import com.example.eventshub.presentation.events.createevent.CreateOrEditEventDialog
import com.example.eventshub.ui.theme.primaryColor
import com.example.eventshub.util.fixLocalhostUrl
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
        viewModel.loadEventCost(event.id)
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
            if(event.imageLink != null){
                if (event.imageLink!!.isNotEmpty()){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(fixLocalhostUrl(event.imageLink!!))
                            .crossfade(true)
                            .listener(
                                onError = { request, throwable ->
                                    Log.e("ImageError", "Failed to load image", throwable.throwable)
                                }
                            )
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        placeholder = painterResource(R.drawable.profile_icon),
                        error = painterResource(R.drawable.event64)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
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
                        spent = viewModel.eventCost ?: 0f,
                        total = event.budget
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                showEditDialog = true
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = primaryColor
                            ),
                            border = BorderStroke(1.dp, primaryColor)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.edit64),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Edit")
                        }

                        OutlinedButton(
                            onClick = { showDeleteDialog = true },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = primaryColor
                            ),
                            border = BorderStroke(1.dp, primaryColor)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.delete64),
                                contentDescription = null
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Delete")
                        }
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Divider()

            Text("Cart", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            val cartServices = viewModel.services.value

            if (cartServices.isEmpty()) {
                Text("No services added to this event yet.", color = Color.Gray)
            } else {
                cartServices.forEach {
                    ServiceCartCard(it, event.id, event.organizerId)
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
                    RegisteredServiceCard(it)
                }
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