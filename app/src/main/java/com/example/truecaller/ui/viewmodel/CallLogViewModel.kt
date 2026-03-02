package com.example.truecaller.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.truecaller.data.AppDatabase
import com.example.truecaller.data.entity.CallLog
import com.example.truecaller.data.repository.CallLogRepository
import kotlinx.coroutines.launch

class CallLogViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: CallLogRepository
    val allCallLogs: LiveData<List<CallLog>>

    init {
        val callLogDao = AppDatabase.getDatabase(application).callLogDao()
        repository = CallLogRepository(callLogDao)
        allCallLogs = repository.allCallLogs.asLiveData()
    }

    fun insert(callLog: CallLog) = viewModelScope.launch {
        repository.insert(callLog)
    }
}
