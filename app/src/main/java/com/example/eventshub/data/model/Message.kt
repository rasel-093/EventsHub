package com.example.eventshub.data.model

data class Message(
    val id: Long = 0,
    val senderId: Long,
    val receiverId: Long,
    val text: String,
    val imageLink: String? = null,
    val sentAt: Long
)