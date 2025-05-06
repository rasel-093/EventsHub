package com.example.eventshub.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.eventshub.presentation.home.components.SearchBarCustom
import com.example.eventshub.ui.theme.primaryColor
import com.google.gson.Gson
import org.koin.androidx.compose.koinViewModel
import java.net.URLEncoder

//Full code having api integrated. ....
@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state
    var searchQuery by remember { mutableStateOf("") }
    val filteredServices = remember(searchQuery, state.services) {
        if (searchQuery.isBlank()) state.services
        else state.services.filter {
            it.title.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(innerPadding)
    ) {
        SearchBarCustom(query = searchQuery, onQueryChange = { searchQuery = it })

        when {
            state.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Text(
                    text = state.error ?: "Unknown error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    itemsIndexed(filteredServices) { index,service ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .clickable {
                                    //passing service
                                    val gson = Gson()
                                    val encoded = URLEncoder.encode(gson.toJson(service), "UTF-8")
                                    navController.navigate("servicedetails/$encoded")
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
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
                                        color =  primaryColor,
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
    }
}



