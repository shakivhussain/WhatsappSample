package com.shakiv.whatsappsample.utils.imageLoader

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class DiskCache(private val context: Context) {

    private fun getCacheDir(): File {
        return context.cacheDir
    }

    fun saveFile(url: String, bitmap: Bitmap) {
        val file = File(getCacheDir(), url.hashCode().toString())
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
    }

    fun getFileStream(url: String): FileInputStream? {
        val file = File(getCacheDir(), url.hashCode().toString())
        return if (file.exists()) {
            FileInputStream(file)
        } else {
            null
        }
    }
}
