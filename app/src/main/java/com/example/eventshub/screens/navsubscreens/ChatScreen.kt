package com.example.eventshub.screens.navsubscreens

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ChatScreen(organizer: Organizer, innerPadding: PaddingValues, onBackClick: () -> Unit) {
//
//    var messageText by remember { mutableStateOf(TextFieldValue("")) }
//    //TopAppBar(title = { Text(organizer.name) })
//    Scaffold(
//        topBar = { TopBarWithBackButton(topBarText = organizer.name){
//            //navigate back
//            onBackClick()
//        } }
//    ) {ownPadding->
//        Column(modifier = Modifier
//            .padding(innerPadding)
//           // .padding(ownPadding)
//            .background(Color.White)
//            .fillMaxSize()
//        ) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(1f), // Fill available space
//                contentPadding = PaddingValues(16.dp),
//                reverseLayout = true // Display messages from bottom to top
//            ) {
//                items(organizer.messages.reversed()) { message -> // Reverse the list for display
//                    MessageItem(message)
//                }
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                OutlinedTextField(
//                    value = messageText,
//                    onValueChange = { messageText = it },
//                    modifier = Modifier.weight(1f),
//                    placeholder = { Text("Type a message...") },
//                    shape = RoundedCornerShape(16.dp),
//                    colors = TextFieldDefaults.colors(
//                        focusedIndicatorColor = primaryColor,
//                        focusedContainerColor = Color.White,
//                        unfocusedContainerColor = Color.White
//                    )
//                )
//
//                Spacer(modifier = Modifier.width(8.dp))
//
//                IconButton(onClick = {
//                    messageText = TextFieldValue("")
//                }) {
//                    Icon(imageVector = Icons.Default.Send, contentDescription = "Send", tint = primaryColor)
//                }
//            }
//        }
//    }
//}