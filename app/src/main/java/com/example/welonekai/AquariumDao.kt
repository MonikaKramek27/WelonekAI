package com.example.welonekai

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AquariumDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAquarium(aquarium: Aquarium)

    @Delete
    suspend fun deleteAquarium(aquarium: Aquarium)

    @Query("SELECT * FROM aquariums ORDER BY name ASC")
    fun getAllAquariums(): Flow<List<Aquarium>>

    @Query("SELECT * FROM aquariums WHERE id = :aquariumId")
    fun getAquariumById(aquariumId: Long): Flow<Aquarium>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogEntry(logEntry: LogEntry)

    @Query("SELECT * FROM log_entries WHERE aquariumId = :aquariumId ORDER BY timestamp DESC")
    fun getLogEntriesForAquarium(aquariumId: Long): Flow<List<LogEntry>>
}
