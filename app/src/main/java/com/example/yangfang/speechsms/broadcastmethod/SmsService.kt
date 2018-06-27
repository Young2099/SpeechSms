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
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val msg = intent!!.getStringExtra("msg")
        Log.e("内容:", msg)
        Toast.makeText(MyApplication.instance, intent.getStringExtra("msg"), Toast.LENGTH_LONG).show()
        /**
         * 讯飞语音播报
         */
        TtsWarpper.with(MyApplication.instance)
                .init()
                .create()
                .set(msg)
                .speech()
        return START_STICKY
    }


}
