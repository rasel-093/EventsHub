package com.example.eventshub.data.model

data class ImageGenerateMessage(
    val id: Long,
    val senderId: Long,
    val text: String,
    val imageLink: String?
)