package com.shakiv.whatsappsample.utils.imageLoader


sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure(val exception: Exception) : Result<Nothing>()
}