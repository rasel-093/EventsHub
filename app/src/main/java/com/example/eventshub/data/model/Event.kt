package com.example.eventshub.data.model
//data class Event(
//    val id: Long,
//    val title: String,
//    val description: String,
//    val date: String,
//    val location: String
//)
data class Event(
    var id:Long =0,
    val name: String,
    val description: String,
    var organizerId:Long,
    val imageLink: String?,
    val date:Long,
    val budget:Float
)
