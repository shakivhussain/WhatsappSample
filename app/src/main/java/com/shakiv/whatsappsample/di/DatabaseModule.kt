package com.shakiv.whatsappsample.di

import android.content.Context
import androidx.room.Room
import com.shakiv.whatsappsample.data.database.AppDatabase
import com.shakiv.whatsappsample.data.database.MessageDao
import com.shakiv.whatsappsample.data.database.UserDao
import com.shakiv.whatsappsample.utils.AppUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, AppUtils.APP_DATA_BASE_NAME)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao = appDatabase.getUserDao()

    @Provides
    fun provideMessageDao(appDatabase: AppDatabase): MessageDao = appDatabase.getMessageDao()


}