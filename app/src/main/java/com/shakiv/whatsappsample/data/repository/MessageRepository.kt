package com.shakiv.whatsappsample.data.repository

import androidx.paging.PagingData
import com.shakiv.whatsappsample.data.model.Message
import com.shakiv.whatsappsample.data.model.User
import kotlinx.coroutines.flow.Flow

interface MessageRepository {

    suspend fun getAllMessages(userId:Long) : Flow<PagingData<Message>>
    suspend fun addMessage(message: Message) :Flow<Long>
    suspend fun updateMessage(message: Message)

}