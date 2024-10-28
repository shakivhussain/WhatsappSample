package com.shakiv.whatsappsample.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.shakiv.whatsappsample.data.database.UserDao
import com.shakiv.whatsappsample.data.model.Message
import com.shakiv.whatsappsample.data.model.User
import com.shakiv.whatsappsample.data.paging.UserPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImp @Inject constructor(
    private val userDao: UserDao
) : UserRepository {


    override suspend fun insertUser(user: List<User>) = userDao.insertUser(user)

    override fun getUsers(query: String): Flow<PagingData<User>> {

        return Pager(
            PagingConfig(
                pageSize = 5,
                initialLoadSize = 5,
                enablePlaceholders = false,

                )
        ) {
            UserPagingSource(userDao, query)
        }.flow

    }

    override fun getUserId(userId: String) = userDao.getUserId(userId)

    override fun updateUser(userId: Long, lastMessage: Message) {
        userDao.updateUser(userId, lastMessage)
    }

}