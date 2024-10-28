package com.shakiv.whatsappsample.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.shakiv.whatsappsample.data.model.Message

class MessageConverter {
    val json = Gson()

    @TypeConverter
    fun toMessage(message: Message?): String {
        return json.toJson(message)
    }

    @TypeConverter
    fun fromPoll(message: String): Message? {
        return try {
            Gson().fromJson(message, Message::class.java)
        } catch (e: Exception) {
            null
        }

    }
}