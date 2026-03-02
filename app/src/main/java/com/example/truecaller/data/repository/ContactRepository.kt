package com.example.truecaller.data.repository

import com.example.truecaller.data.dao.ContactDao
import com.example.truecaller.data.entity.Contact
import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDao) {
    val allContacts: Flow<List<Contact>> = contactDao.getAllContacts()

    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }

    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }

    suspend fun getContactByPhone(phone: String): Contact? {
        return contactDao.getContactByPhone(phone)
    }
}
