package com.example.eventshub.rowitems

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