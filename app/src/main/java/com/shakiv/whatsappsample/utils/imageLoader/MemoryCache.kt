package com.shakiv.whatsappsample.utils.imageLoader

import android.graphics.Bitmap
import java.util.Collections
import java.util.WeakHashMap

class MemoryCache {

    private val cache = Collections.synchronizedMap(WeakHashMap<String, Bitmap>())

    fun get(url: String): Bitmap? {
        return cache[url]
    }

    fun put(url: String, bitmap: Bitmap) {
        cache[url] = bitmap
    }

    fun clear() {
        cache.clear()
    }
}
