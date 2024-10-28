package com.shakiv.whatsappsample.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Message(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "text") var text: String,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "userId") val userId: Long,
) : Serializable

