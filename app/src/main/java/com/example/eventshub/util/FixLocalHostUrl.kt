package com.example.eventshub.util

import androidx.core.net.toUri

fun fixLocalhostUrl(originalUrl: String): String {
    val uri = originalUrl.toUri()
    return if (uri.host == "localhost") {
        originalUrl.replace("localhost", "10.0.2.2")
    } else {
        originalUrl
    }
}
