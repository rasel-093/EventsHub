package com.example.eventshub.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.eventshub.presentation.home.shared.SharedServiceViewModel
import org.koin.androidx.compose.koinViewModel

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.eventshub.data.model.Service
import com.example.eventshub.presentation.home.components.SearchBarCustom
import com.example.eventshub.ui.theme.primaryColor

import org.koin.androidx.compose.koinViewModel

//@Composable
//fun HomeScreen(
//    innerPadding: PaddingValues,
//    navController: NavHostController,
//    viewModel: HomeViewModel = koinViewModel(),
//    sharedServiceViewModel: SharedServiceViewModel = koinViewModel()
//) {
//    val state by viewModel.state
//
//    // Cache the list in shared ViewModel
//    LaunchedEffect(state.services) {
//        if (state.services.isNotEmpty()) {
//            sharedServiceViewModel.setServices(state.services)
//        }
//    }
//
//    Column(modifier = Modifier.padding(innerPadding)) {
//        when {
//            state.isLoading -> {
//                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    CircularProgressIndicator()
//                }
//            }
//
//            state.error != null -> {
//                Text(
//                    text = state.error ?: "Unknown error",
//                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//
//            else -> {
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(state.services) { service ->
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(8.dp)
//                                .clickable {
//                                    navController.navigate("servicedetails/${service.id}")
//                                },
//                            elevation = CardDefaults.cardElevation(4.dp)
//                        ) {
//                            Row(modifier = Modifier.padding(16.dp)) {
//                                AsyncImage(
//                                    model = service.imageLink,
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .size(80.dp)
//                                        .padding(end = 16.dp)
//                                )
//                                Column {
//                                    Text(text = service.title, style = MaterialTheme.typography.titleMedium)
//                                    Text(text = "Rating: ${service.rating}")
//                                    Text(text = "Fee: $${service.fee}")
//                                    Text(text = "Type: ${service.serviceType}")
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//@Composable
//fun HomeScreen(
//    innerPadding: PaddingValues,
//    navController: NavHostController,
//    viewModel: HomeViewModel = koinViewModel(),
//    sharedServiceViewModel: SharedServiceViewModel = koinViewModel()
//) {
//    val state by viewModel.state
//    var searchQuery by remember { mutableStateOf("") }
//
//    // Cache services in shared viewmodel
//    LaunchedEffect(state.services) {
//        if (state.services.isNotEmpty()) {
//            sharedServiceViewModel.setServices(state.services)
//        }
//    }
//
//    val filteredServices = remember(searchQuery, state.services) {
//        if (searchQuery.isBlank()) state.services
//        else state.services.filter {
//            it.title.contains(searchQuery, ignoreCase = true)
//        }
//    }
//
//    Column(modifier = Modifier.padding(innerPadding)) {
//        SearchBarCustom(query = searchQuery, onQueryChange = { searchQuery = it })
//
//        when {
//            state.isLoading -> {
//                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    CircularProgressIndicator()
//                }
//            }
//
//            state.error != null -> {
//                Text(
//                    text = state.error ?: "Unknown error",
//                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//
//            else -> {
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(filteredServices) { service ->
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(8.dp)
//                                .clickable {
//                                    navController.navigate("servicedetails/${service.id}")
//                                },
//                            elevation = CardDefaults.cardElevation(4.dp)
//                        ) {
//                            Row(modifier = Modifier.padding(16.dp)) {
//                                AsyncImage(
//                                    model = service.imageLink,
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .size(80.dp)
//                                        .padding(end = 16.dp)
//                                )
//                                Column {
//                                    Text(text = service.title, style = MaterialTheme.typography.titleMedium)
//                                    Text(text = "Rating: ${service.rating}")
//                                    Text(text = "Fee: $${service.fee}")
//                                    Text(text = "Type: ${service.serviceType}")
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//@Composable
//fun HomeScreen(
//    innerPadding: PaddingValues,
//    navController: NavHostController // Keep it if you're navigating later
//) {
//    val dummyServices = remember {
//        listOf(
//            Service(1, "Wedding Photography", "Best service", 4.5f, 101, 300.0, "https://via.placeholder.com/150", "Photography"),
//            Service(2, "DJ Night", "Party DJ with sound", 4.2f, 102, 500.0, "https://via.placeholder.com/150", "Entertainment"),
//            Service(3, "Catering Deluxe", "Top-rated catering", 4.8f, 103, 800.0, "https://via.placeholder.com/150", "Catering"),
//            Service(4, "Decoration Expert", "Premium decor service", 4.1f, 104, 400.0, "https://via.placeholder.com/150", "Decoration")
//        )
//    }
//
//    var searchQuery by remember { mutableStateOf("") }
//    val filteredServices = dummyServices.filter {
//        it.title.contains(searchQuery, ignoreCase = true)
//    }
//
//    Column(modifier = Modifier
//        .padding(innerPadding)
//        .fillMaxSize()) {
//
//        SearchBarCustom (query = searchQuery, onQueryChange = { searchQuery = it })
//
//        LazyColumn(modifier = Modifier.fillMaxSize()) {
//            items(filteredServices) { service ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .clickable {
//                            // Navigate to details if needed
//                        },
//                    elevation = CardDefaults.cardElevation(4.dp)
//                ) {
//                    Row(modifier = Modifier.padding(16.dp)) {
//                        AsyncImage(
//                            model = service.imageLink,
//                            contentDescription = null,
//                            modifier = Modifier
//                                .size(80.dp)
//                                .padding(end = 16.dp)
//                        )
//                        Column {
//                            Text(text = service.title, style = MaterialTheme.typography.titleMedium)
//                            Text(text = "Rating: ${service.rating}")
//                            Text(text = "Fee: $${service.fee}")
//                            Text(text = "Type: ${service.serviceType}")
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

//Only for ui. Api integration removed. Test UI only

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    navController: NavHostController
) {
    val dummyServices = remember {
        listOf(
            Service(1, "Wedding Photography", "Best service", 4.5f, 101, 300.0, "https://via.placeholder.com/150", "Photography"),
            Service(2, "DJ Night", "Party DJ with sound", 4.2f, 102, 500.0, "https://via.placeholder.com/150", "Entertainment"),
            Service(3, "Catering Deluxe", "Top-rated catering", 4.8f, 103, 800.0, "https://via.placeholder.com/150", "Catering"),
            Service(4, "Decoration Expert", "Premium decor service", 4.1f, 104, 400.0, "https://via.placeholder.com/150", "Decoration")
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    val filteredServices = dummyServices.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(innerPadding)
    ) {
        SearchBarCustom (query = searchQuery, onQueryChange = { searchQuery = it })

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredServices) { service ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = service.imageLink,
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(end = 16.dp)
                        )
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = service.title,
                                color = primaryColor,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Type: ${service.serviceType}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Fee: $${service.fee}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = "Rating: ${service.rating}",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

//Full code having api integrated. ....
//@Composable
//fun HomeScreen(
//    innerPadding: PaddingValues,
//    navController: NavHostController,
//    viewModel: HomeViewModel = koinViewModel(),
//    sharedServiceViewModel: SharedServiceViewModel = koinViewModel()
//) {
//    val state by viewModel.state
//    var searchQuery by remember { mutableStateOf("") }
//
//    // Cache services in shared viewmodel
//    LaunchedEffect(state.services) {
//        if (state.services.isNotEmpty()) {
//            sharedServiceViewModel.setServices(state.services)
//        }
//    }
//
//    val filteredServices = remember(searchQuery, state.services) {
//        if (searchQuery.isBlank()) state.services
//        else state.services.filter {
//            it.title.contains(searchQuery, ignoreCase = true)
//        }
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(innerPadding)
//    ) {
//        SearchBarCustom(query = searchQuery, onQueryChange = { searchQuery = it })
//
//        when {
//            state.isLoading -> {
//                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    CircularProgressIndicator()
//                }
//            }
//
//            state.error != null -> {
//                Text(
//                    text = state.error ?: "Unknown error",
//                    color = MaterialTheme.colorScheme.error,
//                    modifier = Modifier.padding(16.dp)
//                )
//            }
//
//            else -> {
//                LazyColumn(modifier = Modifier.fillMaxSize()) {
//                    items(filteredServices) { service ->
//                        Card(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 12.dp, vertical = 8.dp)
//                                .clickable {
//                                    navController.navigate("servicedetails/${service.id}")
//                                },
//                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
//                            colors = CardDefaults.cardColors(containerColor = Color.White)
//                        ) {
//                            Row(
//                                modifier = Modifier
//                                    .padding(16.dp)
//                                    .fillMaxWidth()
//                            ) {
//                                AsyncImage(
//                                    model = service.imageLink,
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .size(80.dp)
//                                        .padding(end = 16.dp)
//                                )
//                                Column(modifier = Modifier.fillMaxWidth()) {
//                                    Text(
//                                        text = service.title,
//                                        color =  primaryColor,
//                                        style = MaterialTheme.typography.titleMedium
//                                    )
//                                    Spacer(modifier = Modifier.height(4.dp))
//                                    Text(
//                                        text = "Type: ${service.serviceType}",
//                                        style = MaterialTheme.typography.bodySmall
//                                    )
//                                    Text(
//                                        text = "Fee: $${service.fee}",
//                                        style = MaterialTheme.typography.bodySmall
//                                    )
//                                    Text(
//                                        text = "Rating: ${service.rating}",
//                                        style = MaterialTheme.typography.bodySmall
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}



