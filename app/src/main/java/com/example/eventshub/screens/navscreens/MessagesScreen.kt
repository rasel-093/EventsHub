package com.example.eventshub.screens.navscreens

//data class Organizer(
//    val id: Int,
//    val name: String,
//    val profileImage: Int, // Resource ID
//    val messages: List<Message>
//)
//data class Message(val text: String, val isSentByUser: Boolean, val timestamp: LocalDateTime)
//
//@Composable
//fun MessageScreen(organizers: List<Organizer>, onOrganizerSelected: (Int) -> Unit, innerPadding: PaddingValues) {
//    Column(
//        modifier = Modifier.fillMaxSize().background(Color.White)
//        //.padding(innerPadding
//    ) {
//        Text(
//            modifier = Modifier.padding(16.dp),
//            text = "EventsHub",
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            color = primaryColor)
//        LazyColumn(modifier = Modifier
//            .fillMaxSize()
//        ) {
//            itemsIndexed(organizers) {index, organizer ->
//                MessageCard(organizer) { onOrganizerSelected(index) }
//            }
//        }
//    }
//}