package com.example.muzz.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * Represents a message entity in the chat application.
 *
 * @property id Unique identifier for the message.
 * @property content The content of the message.
 * @property isSent Indicates if the message was sent by the current user.
 * @property sender The sender of the message, e.g., "User" or "Other".
 * @property timestamp The date and time when the message was sent.
 * @property isRead Indicates if the message has been read.
 */
@Entity
data class Message(
    @PrimaryKey val id: String,            // Unique identifier for the message
    val content: String,                   // The actual content of the message
    val isSent: Boolean,                   // True if the message is sent by the user, false if received
    val sender: String,                    // The sender of the message, could be "User" or "Other"
    val timestamp: Date,                   // The time when the message was sent
    val isRead: Boolean = false            // True if the message has been read, default is false
)


//Explanation:
//Class-Level Comment: Describes what the class represents in the application.
//Property-Level Comments: Explain the purpose and usage of each property within the Message class.
//Annotations: @Entity and @PrimaryKey are annotations from the Room library that
//designate this class as a database entity and id as the primary key, respectively.