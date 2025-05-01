package com.example.eventshub.util

import android.content.SharedPreferences
import androidx.core.content.edit

fun logout(preferences: SharedPreferences) {
    preferences.edit() {
        remove("userId")
            .remove("token")
            .remove("role").apply()
    }
}
