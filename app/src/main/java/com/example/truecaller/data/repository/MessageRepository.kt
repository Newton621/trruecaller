package com.example.truecaller.data.repository

import com.example.truecaller.data.dao.MessageDao
import com.example.truecaller.data.entity.Message
import kotlinx.coroutines.flow.Flow

class MessageRepository(private val messageDao: MessageDao) {
    val allMessages: Flow<List<Message>> = messageDao.getAllMessages()

    fun getMessagesByThread(threadId: String): Flow<List<Message>> {
        return messageDao.getMessagesByThread(threadId)
    }

    suspend fun insert(message: Message) {
        messageDao.insert(message)
    }

    suspend fun deleteThread(threadId: String) {
        messageDao.deleteThread(threadId)
    }
}
