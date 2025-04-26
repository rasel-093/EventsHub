package com.example.eventshub.presentation.events
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.eventshub.R
import com.example.eventshub.components.EventCard
import com.example.eventshub.ui.theme.primaryColor
import org.koin.androidx.compose.koinViewModel

//@Composable
//fun EventsScreen(upcomingEvents: List<Event>, pastEvents: List<Event>, onCreateEventClick: () -> Unit, innerPadding: PaddingValues, navController: NavHostController) {
//    var selectedTab by remember { mutableIntStateOf(0) }
//    val eventsToShow = if (selectedTab == 0) upcomingEvents else pastEvents
//
//    Column(modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color.White)) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .background(Color.White),
//            horizontalArrangement = Arrangement.SpaceAround
//        ) {
//            Button(onClick = { selectedTab = 0 },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (selectedTab == 0) primaryColor else Color.LightGray
//                )
//            ) {
//                Text("UPCOMING", color = Color.White)
//            }
//
//            Button(onClick = { selectedTab = 1 },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = if (selectedTab == 1) primaryColor else Color.LightGray
//                )
//            ) {
//                Text("PAST EVENTS", color = Color.White)
//            }
//        }
//
//        if (eventsToShow.isEmpty()) {
//            Column(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.event_icon), // Replace with your icon
//                    contentDescription = null,
//                    modifier = Modifier.size(48.dp),
//                    tint = Color.Blue
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//                Text("No Events", fontSize = 20.sp, color = Color.Black)
//                Text("Create an event and make some memories.", fontSize = 14.sp, color = Color.Gray)
//            }
//        } else {
//            LazyColumn {
//                itemsIndexed(eventsToShow) { index, event ->
//                    EventCard(event, navController = navController)
//                }
//            }
//        }
//
//        // Create Event Button (if needed outside bottom nav)
//        Button(onClick = onCreateEventClick, modifier = Modifier.align(Alignment.CenterHorizontally)) {
//            Text("Create Event")
//        }
//    }
//}
@Composable
fun EventsScreen(
    onCreateEventClick: () -> Unit,
    innerPadding: PaddingValues,
    navController: NavHostController,
    viewModel: EventsViewModel = koinViewModel()
) {
    val state by viewModel.state
    var selectedTab by remember { mutableIntStateOf(0) }
    val eventsToShow = if (selectedTab == 0) state.upcomingEvents else state.pastEvents

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = { selectedTab = 0 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTab == 0) primaryColor else Color.LightGray
                )
            ) { Text("UPCOMING", color = Color.White) }

            Button(
                onClick = { selectedTab = 1 },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTab == 1) primaryColor else Color.LightGray
                )
            ) { Text("PAST EVENTS", color = Color.White) }
        }

        if (state.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (eventsToShow.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painter = painterResource(id = R.drawable.event_icon), contentDescription = null, modifier = Modifier.size(48.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text("No Events", fontSize = 20.sp)
                Text("Create an event and make some memories.", fontSize = 14.sp, color = Color.Gray)
            }
        } else {
            LazyColumn {
                itemsIndexed(eventsToShow) { _, event ->
                    EventCard(
                        event, onClick = {
                            navController.navigate("eventdetails/${event.id}")
                        }
                    )
                }
            }
        }

        Button(onClick = onCreateEventClick, modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text("Create Event")
        }
    }
}

