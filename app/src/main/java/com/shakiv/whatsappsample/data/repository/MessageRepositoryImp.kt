package com.shakiv.whatsappsample.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shakiv.whatsappsample.data.database.MessageDao
import com.shakiv.whatsappsample.data.model.Message
import com.shakiv.whatsappsample.data.paging.MessagePagingSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MessageRepositoryImp @Inject constructor(
    private val messageDao: MessageDao
) : MessageRepository {

    override suspend fun getAllMessages(userId:Long): Flow<PagingData<Message>> {
        return Pager(
            PagingConfig(pageSize = 20, prefetchDistance = 4, true, initialLoadSize = 20)
        ){
         MessagePagingSource(messageDao,userId)
        }.flow
    }

    override suspend fun addMessage(message: Message) = flow<Long>{
        emit(messageDao.addMessages(message))
    }

    override suspend fun updateMessage(message: Message) {
        messageDao.updateMessage(message.text,message.id)
    }
}