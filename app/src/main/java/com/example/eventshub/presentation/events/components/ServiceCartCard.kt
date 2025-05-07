package com.example.eventshub.presentation.events.components

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.eventshub.R
import com.example.eventshub.data.model.BookingStatus
import com.example.eventshub.data.model.Service
import com.example.eventshub.data.model.ServiceBookingInfo
import com.example.eventshub.presentation.booking.BookingViewModel
import com.example.eventshub.presentation.events.eventdetails.EventDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServiceCartCard(
    service: Service,
    eventId: Long,
    eventOrganizerId:Long,
    bookingViewModel: BookingViewModel = koinViewModel(),
    eventDetailsViewModel: EventDetailViewModel = koinViewModel()
    ) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = service.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.category64),
                        contentDescription = "Service Type",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = service.serviceType,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.dollar64),
                        contentDescription = "Fee",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$${"%.2f".format(service.fee)}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        val bookingInfo = ServiceBookingInfo(
                            serviceId = service.id,
                            eventOrganizerId = eventOrganizerId,
                            status = BookingStatus.PENDING
                        )
                        Toast.makeText(context, "Service confirmed", Toast.LENGTH_SHORT).show()
                        bookingViewModel.confirmServiceBooking(bookingInfo)
                        bookingViewModel.loadRegisteredServices(eventId)
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text("Confirm")
                }

                IconButton(
                    onClick = {
                        eventDetailsViewModel.removeServiceFromEvent(
                            eventId = eventId,
                            serviceId = service.id
                        )
                        Toast.makeText(context, "Service Deleted", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.error.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove service",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
//Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 4.dp),
//                        elevation = CardDefaults.cardElevation(4.dp)
//                    ) {
//                        Row(
//                            Modifier
//                                .fillMaxWidth()
//                                .padding(12.dp),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Column {
//                                Text(it.title, style = MaterialTheme.typography.titleMedium)
//                                Text("Type: ${it.serviceType}", color = Color.Gray)
//                                Text("Fee: $${it.fee}")
//                            }
//                            Button(onClick = {
//                                val bookingInfo = ServiceBookingInfo(
//                                    serviceId = it.id,
//                                    eventOrganizerId = event.organizerId, // make sure this exists in your Event model
//                                    status = BookingStatus.PENDING
//                                )
//                                bookingViewModel.confirmServiceBooking(bookingInfo)
//                                bookingViewModel.loadRegisteredServices(event.id)
//                            }) {
//                                Text("Confirm")
//                            }
//
//                            IconButton(onClick = {
//                                viewModel.removeServiceFromEvent(
//                                    eventId = event.id,
//                                    serviceId = it.id
//                                )
//                            }) {
//                                Icon(
//                                    imageVector = Icons.Default.Delete,
//                                    contentDescription = "Remove service",
//                                    tint = MaterialTheme.colorScheme.error
//                                )
//                            }
//
//                        }
//                    }