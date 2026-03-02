package com.example.truecaller.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.truecaller.data.dao.CallLogDao
import com.example.truecaller.data.dao.ContactDao
import com.example.truecaller.data.dao.MessageDao
import com.example.truecaller.data.entity.CallLog
import com.example.truecaller.data.entity.Contact
import com.example.truecaller.data.entity.Message

@Database(entities = [Contact::class, Message::class, CallLog::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun messageDao(): MessageDao
    abstract fun callLogDao(): CallLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "truecaller_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
