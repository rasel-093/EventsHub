package com.example.eventshub.rowitems

//@Composable
//fun MessageItem(message: Message) {
//    val formatter = DateTimeFormatter.ofPattern("HH:mm") // Format time as needed
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 4.dp),
//        horizontalAlignment = if (message.isSentByUser) Alignment.End else Alignment.Start
//    ) {
//        // Message Bubble
//        Text(
//            text = message.text,
//            modifier = Modifier
//                .background(
//                    if (message.isSentByUser) Color.Blue else Color.LightGray,
//                    shape = RoundedCornerShape(16.dp)
//                )
//                .padding(8.dp),
//            color = if (message.isSentByUser) Color.White else Color.Black
//        )
//
//        // Timestamp (optional)
//        Text(
//            text = message.timestamp.format(formatter),
//            fontSize = 12.sp,
//            color = Color.Gray,
//            modifier = Modifier.padding(top = 2.dp)
//        )
//    }
//}