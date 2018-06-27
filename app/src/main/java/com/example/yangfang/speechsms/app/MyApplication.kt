package com.example.yangfang.speechsms.app

import android.app.Application
import com.iflytek.cloud.SpeechConstant
import com.iflytek.cloud.SpeechUtility

class MyApplication : Application() {
    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if(SpeechUtility.getUtility() != null){
            SpeechUtility.getUtility().destroy()
        }
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b31daa2")

    }


}