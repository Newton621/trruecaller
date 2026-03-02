package com.example.truecaller.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.truecaller.data.AppDatabase
import com.example.truecaller.data.entity.Message
import com.example.truecaller.data.repository.MessageRepository
import kotlinx.coroutines.launch

class MessageViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MessageRepository
    val allMessages: LiveData<List<Message>>

    init {
        val messageDao = AppDatabase.getDatabase(application).messageDao()
        repository = MessageRepository(messageDao)
        allMessages = repository.allMessages.asLiveData()
    }

    fun insert(message: Message) = viewModelScope.launch {
        repository.insert(message)
    }

    fun getMessagesByThread(threadId: String): LiveData<List<Message>> {
        return repository.getMessagesByThread(threadId).asLiveData()
    }
}
