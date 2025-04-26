package com.example.eventshub.presentation.profile
import android.content.SharedPreferences
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.eventshub.ui.theme.primaryColor
import com.example.eventshub.util.logout
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    innerPadding: PaddingValues,
    navController: NavHostController,
    viewModel: ProfileViewModel = koinViewModel(),
    tabNavController: NavHostController
) {
    val state by viewModel.state
    val preferences: SharedPreferences = koinInject()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile", style = MaterialTheme.typography.titleLarge) },
                actions = {
                    IconButton(onClick = {
                        tabNavController.navigate("editprofile") {
                            launchSingleTop = true
                        }
                    }) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = primaryColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.error != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.error ?: "", color = MaterialTheme.colorScheme.error)
            }
        } else {
            val user = state.user
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile photo box
                Box(
                    modifier = Modifier
                        .padding(top = 32.dp, bottom = 24.dp)
                        .shadow(8.dp, shape = CircleShape)
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile Photo",
                        tint = Color.White,
                        modifier = Modifier.size(60.dp)
                    )
                }

                // Profile info
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        ProfileInfoRow("Name", user?.name ?: "")
                        Divider(Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)
                        ProfileInfoRow("Phone no.", user?.phone ?: "")
                        Divider(Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)
                        ProfileInfoRow("E-Mail", user?.email ?: "")
                        Divider(Modifier.padding(vertical = 12.dp), thickness = 0.5.dp)
                        ProfileInfoRow("Role", user?.role?.name ?: "")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        logout(preferences)
                        navController.navigate("signin") {
                            popUpTo(0) { inclusive = true } // Clears back stack
                            launchSingleTop = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 24.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Sign Out", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}
@Composable
fun ProfileInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}
