package com.shakiv.whatsappsample

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.shakiv.whatsappsample.utils.imageLoader.ImageLoader
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WhatsappApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        ImageLoader.init(applicationContext)

    }
}