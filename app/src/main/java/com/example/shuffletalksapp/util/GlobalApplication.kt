package com.example.shuffletalksapp.util

import android.app.Application
import android.content.Context

class GlobalApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: GlobalApplication
        fun getContext(): Context? {
            return instance.applicationContext
        }
    }
}