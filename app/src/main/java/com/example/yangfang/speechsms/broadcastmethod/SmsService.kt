package com.example.yangfang.speechsms.broadcastmethod

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.yangfang.kotlindemo.util.SharedPreferenceUtil
import com.example.yangfang.speechsms.app.MyApplication
import com.example.yangfang.speechsms.tts.TtsWarpper
import com.example.yangfang.speechsms.util.TtsUtil

class SmsService : Service() {

    private var getPhone by SharedPreferenceUtil("user", "")
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {
        super.onCreate()
        Toast.makeText(MyApplication.instance!!, "启动短信语音", 0).show()
        Log.e("OnCreate", "onCreate:$getPhone")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val msg = intent!!.getStringExtra("msg")
        Log.e("onStartCommand:", "onStartCommand")
        Log.e("内容:", msg)
        Toast.makeText(MyApplication.instance, intent.getStringExtra("msg"), 0).show()
        TtsWarpper.with(MyApplication.instance)
                .init()
                .create()
                .set(msg)
                .start()
        return START_STICKY
    }


}
