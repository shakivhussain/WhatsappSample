package com.shakiv.whatsappsample.utils.imageLoader


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

object ImageLoader {
    private lateinit var diskCache: DiskCache
    private val memoryCache = MemoryCache()

    fun init(context: Context) {
        diskCache = DiskCache(context)
    }

    suspend fun loadImage(url: String): Result<Bitmap> {

        if (!::diskCache.isInitialized) {
            throw IllegalStateException("ImageLoader not initialized. Call initialize() first.")
        }

        memoryCache.get(url)?.let {
            return Result.Success(it)
        }

        diskCache.getFileStream(url)?.use { inputStream ->
            return BitmapFactory.decodeStream(inputStream)?.let {
                memoryCache.put(url, it)
                Result.Success(it)
            } ?: Result.Failure(Exception("Failed to decode bitmap from disk cache"))
        }

        return downloadImage(url)?.let {
            memoryCache.put(url, it)
            diskCache.saveFile(url, it)
            Result.Success(it)
        } ?: Result.Failure(Exception("Failed to download image"))
    }

    private suspend fun downloadImage(url: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                BitmapFactory.decodeStream(connection.inputStream)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
