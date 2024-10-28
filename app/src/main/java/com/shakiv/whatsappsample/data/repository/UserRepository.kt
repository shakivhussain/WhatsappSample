package com.shakiv.whatsappsample.data.repository

import androidx.paging.PagingData
import com.shakiv.whatsappsample.data.model.Message
import com.shakiv.whatsappsample.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: List<User>)

    fun getUsers(query:String): Flow<PagingData<User>>

    fun getUserId(userId:String) : User?

    fun updateUser(userId: Long, lastMessage: Message)
}