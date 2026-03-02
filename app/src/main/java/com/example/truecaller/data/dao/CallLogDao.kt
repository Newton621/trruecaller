package com.example.truecaller.data.dao

import androidx.room.*
import com.example.truecaller.data.entity.CallLog
import kotlinx.coroutines.flow.Flow

@Dao
interface CallLogDao {
    @Query("SELECT * FROM call_logs ORDER BY timestamp DESC")
    fun getAllCallLogs(): Flow<List<CallLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(callLog: CallLog)

    @Query("DELETE FROM call_logs")
    suspend fun deleteAll()
}
