package com.example.eventshub.navigations

import com.example.eventshub.R

sealed class BottomNavItem(val route: String, val iconResId: Int, val title: String) {
    object Home : BottomNavItem("home", R.drawable.home_icon , "Home")
    object Events : BottomNavItem("events", R.drawable.event_icon, "Events")
    object Messages : BottomNavItem("messages", R.drawable.message_icon, "Messages")
    object Profile : BottomNavItem("profile", R.drawable.profile_icon, "Profile")
}