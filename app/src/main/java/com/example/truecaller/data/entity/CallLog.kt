package com.example.truecaller.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "call_logs")
data class CallLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val phoneNumber: String,
    val timestamp: Long,
    val duration: Long,
    val type: Int // 1: Incoming, 2: Outgoing, 3: Missed
)
