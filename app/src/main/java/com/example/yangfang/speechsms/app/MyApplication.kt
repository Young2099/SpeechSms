package com.example.yangfang.speechsms.app

import android.app.Application

class MyApplication : Application() {
    companion object {
        var instance: Application? = null
        fun instance() = instance!!
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}