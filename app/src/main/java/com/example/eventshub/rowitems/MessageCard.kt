package com.example.eventshub.rowitems

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.eventshub.R
import java.time.format.DateTimeFormatter
//@Composable
//fun MessageCard(organizer: Organizer, onOrganizerSelected: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//           // .background(color = Color.White)
//            .padding(vertical = 4.dp, horizontal = 8.dp)
//            .clickable { onOrganizerSelected() },
//        shape = MaterialTheme.shapes.medium,
//        colors = CardDefaults.cardColors(
//            containerColor = Color.White
//        )
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(12.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Profile Icon
//            Image(
//                painter = painterResource(id = R.drawable.profile_icon), // Default profile icon
//                contentDescription = "Profile picture",
//                modifier = Modifier
//                    .size(48.dp)
//                    .clip(CircleShape)
//                    .background(MaterialTheme.colorScheme.primaryContainer)
//            )
//
//            Spacer(modifier = Modifier.width(12.dp))
//
//            // Message Details
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                // Service Provider Name
//                Text(
//                    text = organizer.name,
//                    style = MaterialTheme.typography.titleMedium,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//
//                Spacer(modifier = Modifier.height(4.dp))
//
//                // Last Message Preview
//                Text(
//                    text = organizer.messages.lastOrNull()?.text ?: "No messages yet",
//                    style = MaterialTheme.typography.bodyMedium,
//                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis
//                )
//            }
//
//            // Message Time
//            Text(
//                text = organizer.messages.lastOrNull()?.let {
//                    val formatter = DateTimeFormatter.ofPattern("hh:mm a")
//                    it.timestamp.format(formatter)
//                } ?: "",
//                style = MaterialTheme.typography.labelSmall,
//                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
//                modifier = Modifier.align(Alignment.Top)
//            )
//        }
//    }
//}