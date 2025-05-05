package com.example.eventshub.presentation.booking
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.eventshub.R
import com.example.eventshub.data.model.BookingStatus
import com.example.eventshub.data.model.BookingWithServiceDetails
import com.example.eventshub.ui.theme.primaryColor
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
@Composable
fun BookingScreen(
    viewModel: BookingViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Approved")

    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        if (viewModel.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            val bookings = if (selectedTabIndex == 0)
                                viewModel.allBookings
                            else
                                viewModel.allBookings.filter { it.bookingStatus == BookingStatus.APPROVED }

            Log.d("BookingScreen", "Bookings: ${bookings.size}")

            LazyColumn {
                items(bookings) { booking ->
                    BookingCard(booking = booking,
                        onApprove = {
                            scope.launch {
                                viewModel.updateBooking(
                                    id = booking.bookingId,
                                    status = BookingStatus.APPROVED
                                )
                                viewModel.loadBookings()
                            }
                        },
                        onReject = {
                            scope.launch {
                                viewModel.updateBooking(
                                    id = booking.bookingId,
                                    status = BookingStatus.REJECTED
                                )
                                viewModel.loadBookings()
                            }
                        }
                    )
                }
            }
        }
    }
}
@Composable
fun BookingCard(
    booking: BookingWithServiceDetails,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val surfaceColor = MaterialTheme.colorScheme.surface
    val successColor = MaterialTheme.colorScheme.tertiary
    val errorColor = MaterialTheme.colorScheme.error

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header with service title and status chip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = booking.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = primaryColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Status chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            when (booking.bookingStatus) {
                                BookingStatus.APPROVED -> successColor.copy(alpha = 0.2f)
                                BookingStatus.REJECTED -> errorColor.copy(alpha = 0.2f)
                                else -> MaterialTheme.colorScheme.secondaryContainer
                            }
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = booking.bookingStatus.name.lowercase().replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelMedium,
                        color = when (booking.bookingStatus) {
                            BookingStatus.APPROVED -> successColor
                            BookingStatus.REJECTED -> errorColor
                            else -> MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Booking details
            Column {
                BookingDetailRow(
                    icon =  painterResource(R.drawable.event64),//Icons.Default.DateRange,
                    label = "Event",
                    value = booking.eventName
                )

                BookingDetailRow(
                    icon =  painterResource(R.drawable.event_icon) ,//Icons.Default.DateRange,
                    label = "Date",
                    value = SimpleDateFormat("MMM dd, yyyy - hh:mm a").format(Date(booking.eventDate))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons
            when (booking.bookingStatus) {
                BookingStatus.PENDING -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = onReject,
                            modifier = Modifier.padding(end = 8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = errorColor
                            ),
                            border = BorderStroke(1.dp, errorColor)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Reject",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Reject")
                        }

                        Button(
                            onClick = onApprove,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = successColor,
                                contentColor = Color.White
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Approve",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Approve")
                        }
                    }
                }
                else -> {
                    Text(
                        text = "Booking ${booking.bookingStatus.name.lowercase()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
        }
    }
}

@Composable
private fun BookingDetailRow(icon: Painter, label: String, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = label,
            modifier = Modifier.size(18.dp),
           // colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}



