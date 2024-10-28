package com.shakiv.whatsappsample.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shakiv.whatsappsample.data.model.Message

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMessages(message: Message) : Long

    @Query("SELECT * FROM message WHERE userId = :userId ORDER BY date ASC LIMIT :limit OFFSET :offset")
    fun getAllMessages(userId:Long, limit:Int, offset:Int): List<Message>

    @Query("UPDATE message SET text =:text WHERE id =:id")
    fun updateMessage(text:String, id:Int)
}