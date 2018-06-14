package com.example.yangfang.speechsms

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