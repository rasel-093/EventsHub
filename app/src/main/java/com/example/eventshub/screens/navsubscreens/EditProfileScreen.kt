package com.example.eventshub.screens.navsubscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eventshub.R
import com.example.eventshub.components.ButtonFullWidth
import com.example.eventshub.components.EditPasswordField
import com.example.eventshub.components.EditTextField
import com.example.eventshub.components.TopBarWithBackButton
import com.example.eventshub.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    innerPadding: PaddingValues,
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("Amy Young") }
    var phone by remember { mutableStateOf("+98 1245560090") }
    var email by remember { mutableStateOf("amyoung@random.com") }
    var address by remember { mutableStateOf("123 Main St, Tech City") }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBarWithBackButton(topBarText = "Edit Profile") {
                onBack()
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(bottom = innerPadding.calculateBottomPadding())
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
        ) {
            // Profile Photo Section
            Box(
                modifier = Modifier
                    .padding(vertical = 24.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier
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
                IconButton(
                    onClick = { /* Change photo */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(primaryColor, CircleShape)
                        .padding(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.camera128),
                        contentDescription = "Change Photo",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            EditTextField(name, "Name"){name = it}
            Spacer(modifier = Modifier.height(16.dp))
            EditTextField(phone,"Phone Number") { phone = it}
            Spacer(modifier = Modifier.height(16.dp))
            EditTextField(email, "Email") { email = it }
            Spacer(modifier = Modifier.height(16.dp))
            EditTextField(address, "Address") { address = it}
            Spacer(modifier = Modifier.height(24.dp))

            // Password Change Section
            Text(
                text = "Change Password",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            EditPasswordField(oldPassword) { oldPassword = it}
            Spacer(modifier = Modifier.height(16.dp))
            EditPasswordField(newPassword) { newPassword = it }
            Spacer(modifier = Modifier.height(20.dp))
            ButtonFullWidth("Save Changes") {
                onSave()
            }
        }
    }
}