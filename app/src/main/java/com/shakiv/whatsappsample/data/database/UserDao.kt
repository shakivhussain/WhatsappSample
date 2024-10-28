package com.shakiv.whatsappsample.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shakiv.whatsappsample.data.model.Message
import com.shakiv.whatsappsample.data.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser( user: List<User>)

    @Query("SELECT * FROM user ORDER BY username ASC LIMIT :limit OFFSET :offset")
    fun getAllUser(limit: Int, offset: Int): List<User>

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserId(userId: String): User?

    @Query("SELECT * FROM user  WHERE (username LIKE '%' || :searchQuery || '%' OR last_message LIKE '%' || :searchQuery || '%' )  COLLATE NOCASE ")
    fun searchUsers(searchQuery: String): List<User>

    @Query("UPDATE user  SET last_message =:lastMessage WHERE userId =:userId")
    fun updateUser(userId: Long, lastMessage: Message)

}