package com.example.truecaller.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val threadId: String, // Can be phoneNumber
    val sender: String, // Phone number or "MPESA"
    val content: String,
    val timestamp: Long,
    val isRead: Boolean = false,
    val type: String = "SMS" // "SMS" or "MPESA"
)
