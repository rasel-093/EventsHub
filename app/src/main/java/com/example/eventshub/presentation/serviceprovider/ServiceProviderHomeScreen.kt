package com.example.eventshub.presentation.serviceprovider

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.eventshub.data.model.Service
import com.example.eventshub.ui.theme.primaryColor
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ServiceProviderHomeScreen(viewModel: ServiceProviderViewModel = koinViewModel()) {
    val services by viewModel.services
    val isLoading by viewModel.isLoading
    val snackbarMessage by viewModel.snackbarMessage
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var selectedForEdit by remember { mutableStateOf<Service?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }, containerColor = primaryColor) {
                Icon(Icons.Default.Add, contentDescription = "Add Service")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.White
    ) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)) {

            Text("Your Services", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(12.dp))

            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (services.isEmpty()) {
                Text("You haven't added any services yet.")
            } else {
                LazyColumn {
                    items(services) { service ->
                        ServiceCard(
                            service = service,
                            onDelete = { viewModel.deleteService(it) },
                            onEdit = {
                                selectedForEdit = it
                            }
                        )
                    }
                }

            }
        }
    }

    if (showDialog || selectedForEdit != null) {
        CreateOrEditServiceDialog(
            initial = selectedForEdit,
            onDismiss = {
                showDialog = false
                selectedForEdit = null
            },
            onConfirm = {
                if (selectedForEdit != null) {
                    viewModel.updateService(it)
                } else {
                    viewModel.createService(it)
                    Toast.makeText(context, "Service Created", Toast.LENGTH_SHORT).show()
                }
                selectedForEdit = null
                showDialog = false
            }
        )
    }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            scope.launch {
                snackbarHostState.showSnackbar(it)
                viewModel.clearSnackbarMessage()
            }
        }
    }

}
