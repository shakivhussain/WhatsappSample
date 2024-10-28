package com.shakiv.whatsappsample.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shakiv.whatsappsample.data.database.converter.MessageConverter
import com.shakiv.whatsappsample.data.model.Message
import com.shakiv.whatsappsample.data.model.User

@Database(entities = [Message::class, User::class], version = 1)
@TypeConverters(MessageConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
    abstract fun getMessageDao(): MessageDao
}