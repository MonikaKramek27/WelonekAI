package com.example.welonekai

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "log_entries",
    foreignKeys = [ForeignKey(
        entity = Aquarium::class,
        parentColumns = ["id"],
        childColumns = ["aquariumId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class LogEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val aquariumId: Long,
    val ph: Double,
    val waterChanged: Boolean,
    val notes: String,
    val timestamp: Long = System.currentTimeMillis()
)
