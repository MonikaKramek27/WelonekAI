package com.example.welonekai

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "aquariums")
data class Aquarium(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String
)
