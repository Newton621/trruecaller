package com.example.truecaller.data.repository

import com.example.truecaller.data.dao.CallLogDao
import com.example.truecaller.data.entity.CallLog
import kotlinx.coroutines.flow.Flow

class CallLogRepository(private val callLogDao: CallLogDao) {
    val allCallLogs: Flow<List<CallLog>> = callLogDao.getAllCallLogs()

    suspend fun insert(callLog: CallLog) {
        callLogDao.insert(callLog)
    }

    suspend fun deleteAll() {
        callLogDao.deleteAll()
    }
}
