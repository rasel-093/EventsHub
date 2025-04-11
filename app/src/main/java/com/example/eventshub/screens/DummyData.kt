package com.example.eventshub.screens

import com.example.eventshub.R
import com.example.eventshub.screens.navscreens.Message
import com.example.eventshub.screens.navscreens.Organizer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getDummyOrganizers(): List<Organizer> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val profileImageId = R.drawable.profile_icon // Use your actual profile image resource ID

    return listOf(
        Organizer(
            id = 1,
            name = "Flower Paradise",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Hi there! We have fresh roses available this week",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-15 10:30", formatter)
                )
            )
        ),
        Organizer(
            id = 2,
            name = "Elegant Catering",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Your menu has been confirmed for Saturday",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-14 15:45", formatter)
                )
            )
        ),
        Organizer(
            id = 3,
            name = "Sound Masters",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "We'll need the venue dimensions for our equipment setup",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-14 09:20", formatter)
                )
            )
        ),
        Organizer(
            id = 4,
            name = "Dream Photography",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Can we schedule a pre-event meeting?",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-13 16:10", formatter)
                )
            )
        ),
        Organizer(
            id = 5,
            name = "Venue Solutions",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Your deposit has been received",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-13 11:05", formatter)
                )
            )
        ),
        // Continue with more organizers...
        Organizer(
            id = 6,
            name = "Party Planners Inc.",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "We've added balloons to your decor package",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-12 14:30", formatter)
                )
            )
        ),
        Organizer(
            id = 7,
            name = "Gourmet Delights",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Please confirm any dietary restrictions",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-12 10:15", formatter)
                )
            )
        ),
        Organizer(
            id = 8,
            name = "Lighting Experts",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "We have special disco lights available",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-11 17:20", formatter)
                )
            )
        ),
        Organizer(
            id = 9,
            name = "Wedding Coordinators",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Your timeline draft is ready for review",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-11 13:40", formatter)
                )
            )
        ),
        Organizer(
            id = 10,
            name = "DJ Services",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Please share your music preferences",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-10 18:25", formatter)
                )
            )
        ),
        // Add more organizers up to 20...
        Organizer(
            id = 11,
            name = "Event Security",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Security plan has been finalized",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-10 12:15", formatter)
                )
            )
        ),
        Organizer(
            id = 12,
            name = "Tent Rentals",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "We recommend the 20x30 tent for your event",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-09 14:50", formatter)
                )
            )
        ),
        Organizer(
            id = 13,
            name = "Transport Services",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "3 limos confirmed for your wedding",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-09 11:30", formatter)
                )
            )
        ),
        Organizer(
            id = 14,
            name = "Printing Services",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Invitation proofs are ready",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-08 16:45", formatter)
                )
            )
        ),
        Organizer(
            id = 15,
            name = "Valet Parking",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "We'll need 4 staff for your event",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-08 10:20", formatter)
                )
            )
        ),
        Organizer(
            id = 16,
            name = "Makeup Artists",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Available for trial session next week",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-07 15:10", formatter)
                )
            )
        ),
        Organizer(
            id = 17,
            name = "Event Insurance",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Your policy documents are attached",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-07 09:45", formatter)
                )
            )
        ),
        Organizer(
            id = 18,
            name = "A/V Technicians",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "We'll need your presentation files in advance",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-06 14:15", formatter)
                )
            )
        ),
        Organizer(
            id = 19,
            name = "Floral Designs",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Peonies are in season for your event",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-06 11:05", formatter)
                )
            )
        ),
        Organizer(
            id = 20,
            name = "Wedding Cake Co.",
            profileImage = profileImageId,
            messages = listOf(
                Message(
                    text = "Sample tasting scheduled for Friday",
                    isSentByUser = false,
                    timestamp = LocalDateTime.parse("2023-06-05 16:30", formatter)
                )
            )
        )
    )
}
data class Event(val name: String, val date: String)
fun getDummyUpcomingEvents(): List<Event> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return listOf(
        Event("Birthday Party", LocalDate.parse("2024-01-15", formatter).toString()),
        Event("Wedding Anniversary", LocalDate.parse("2024-02-20", formatter).toString()),
        Event("Conference", LocalDate.parse("2024-03-10", formatter).toString()),
        Event("Music Festival", LocalDate.parse("2024-04-05", formatter).toString())
    )
}
fun getDummyPastEvents(): List<Event> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return listOf(
        Event("Graduation Ceremony", LocalDate.parse("2023-06-25", formatter).toString()),
        Event("Summer Vacation Trip", LocalDate.parse("2023-07-10", formatter).toString()),
        Event("Family Reunion", LocalDate.parse("2023-08-20", formatter).toString()),
        Event("Holiday Party", LocalDate.parse("2023-12-24", formatter).toString())
    )
}