package me.nyaken

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Global: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}