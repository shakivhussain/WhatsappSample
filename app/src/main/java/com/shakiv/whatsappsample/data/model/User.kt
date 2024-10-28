package com.shakiv.whatsappsample.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "user")
data class User(
    @ColumnInfo(name = "userId") @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    @ColumnInfo(name = "username") val username: String = "",
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "profile_url") val profileUrl: String,
    @ColumnInfo(name = "last_message") val lastMessage: Message,
) : Serializable
