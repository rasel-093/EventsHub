package com.example.eventshub.navigations

import com.example.eventshub.R

sealed class BottomNavItem(val route: String, val iconResId: Int, val title: String) {
    data object Home : BottomNavItem("home", R.drawable.home_icon , "Home")
    data object Events : BottomNavItem("events", R.drawable.event_icon, "Events")
    data object Messages : BottomNavItem("messages", R.drawable.message_icon, "Messages")
    data object Profile : BottomNavItem("profile", R.drawable.profile_icon, "Profile")
}