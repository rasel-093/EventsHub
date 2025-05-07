package com.example.eventshub.presentation.home.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.eventshub.R
import com.example.eventshub.components.TopBarWithBackButton
import com.example.eventshub.data.model.Event
import com.example.eventshub.data.model.Service
import com.example.eventshub.presentation.events.EventsViewModel
import com.example.eventshub.ui.theme.primaryColor
import com.example.eventshub.util.fixLocalhostUrl
import com.example.eventshub.util.formatMillisToReadableDateTime
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServiceDetailScreen(
    service: Service,
    viewModel: EventsViewModel = koinViewModel(),
    detailViewModel: ServiceDetailViewModel = koinViewModel(),
    navController: NavHostController
) {
    var showSelection by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Event?>(null) }
    val state by viewModel.state
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val serviceState by detailViewModel.service.collectAsState()
    val updatedService = serviceState ?: service

    LaunchedEffect(detailViewModel.snackbarMessage) {
        detailViewModel.snackbarMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                detailViewModel.clearSnackbar()
            }
        }
    }
    Scaffold(
        topBar = {
            TopBarWithBackButton(
                topBarText = "Service Details",
                { navController.navigateUp() })
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            val imageUrl = fixLocalhostUrl(service.imageLink)
            AsyncImage(
                model = imageUrl,
                contentDescription = "Event Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.decorator128)
            )
            // Service Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(updatedService.title, style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(8.dp))
                    Text(updatedService.description, style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.height(12.dp))
                    // Use updatedService.rating here
                    Text(
                        "⭐ Rating: ${updatedService.rating}",
                        color = Color(0xFF357AEA),
                        fontWeight = FontWeight.Medium
                    )
                    Text("💰 Fee: $${updatedService.fee}", fontWeight = FontWeight.SemiBold)
                    Text("📌 Type: ${updatedService.serviceType}", color = Color.Gray)
                }
            }
            RatingSection(service.id)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        navController.navigate("chat/${service.serviceProviderId}/${service.title}")
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = primaryColor
                    ),
                    border = BorderStroke(1.dp, primaryColor)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.message64),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Message")
                }

                OutlinedButton(
                    onClick = { showSelection = !showSelection },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = primaryColor
                    ),
                    border = BorderStroke(1.dp, primaryColor)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.cart64),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(if (showSelection) "Cancel" else "Add to Cart")
                }
            }
            Spacer(Modifier.height(16.dp))
            AnimatedVisibility(visible = showSelection) {
                Column {
                    val events = state.upcomingEvents

                    Text(
                        "Select an event to add this service:",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(Modifier.height(8.dp))

                    events.forEach { item ->
                        val isSelected = selectedItem == item

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable { selectedItem = item },
                            elevation = CardDefaults.cardElevation(if (isSelected) 6.dp else 2.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Color(0xFFA0CBF1) else Color(
                                    0xFFF9F9F9
                                )
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(item.name, fontWeight = FontWeight.Medium)
                                Text(
                                    formatMillisToReadableDateTime(item.date),
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                    val context = LocalContext.current
                    selectedItem?.let {
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                detailViewModel.addServiceToEvent(service.id, it.id)
                                Toast.makeText(
                                    context,
                                    "Service Added Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                showSelection = false
                                selectedItem = null
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            enabled = !detailViewModel.isLoading,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF357AEA))
                        ) {
                            if (detailViewModel.isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    strokeWidth = 2.dp,
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                Text("Confirm", color = Color.White)
                            }
                        }

                    }
                }
            }
        }
    }
}

