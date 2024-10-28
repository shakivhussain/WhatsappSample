package com.shakiv.whatsappsample.di

import com.shakiv.whatsappsample.data.database.MessageDao
import com.shakiv.whatsappsample.data.database.UserDao
import com.shakiv.whatsappsample.data.repository.MessageRepository
import com.shakiv.whatsappsample.data.repository.MessageRepositoryImp
import com.shakiv.whatsappsample.data.repository.UserRepository
import com.shakiv.whatsappsample.data.repository.UserRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideMessageRepository(messageDao: MessageDao): MessageRepository{
        return MessageRepositoryImp(messageDao)
    }

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository{
        return UserRepositoryImp(userDao)
    }

}