package com.example.yangfang.speechsms

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.yangfang.kotlindemo.util.SharedPreferenceUtil
import com.example.yangfang.speechsms.app.MyApplication
import java.util.*

class SmsService : Service(), TextToSpeech.OnInitListener {
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = speech!!.setLanguage(Locale.CHINA)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "失败", Toast.LENGTH_LONG).show()
        }
    }

    private var speech: TextToSpeech? = null

    private var getPhone by SharedPreferenceUtil("user", "")
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }



    override fun onCreate() {
        super.onCreate()
        Toast.makeText(MyApplication.instance!!, "服务启动", 0).show()
        Log.e("OnCreate", "onCreate:$getPhone")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val msg = intent!!.getStringExtra("msg")
        Log.e("onStartCommand:", "onStartCommand")
        Log.e("内容:", msg)
//        contentResolver.notifyChange(SMS_INBOX, null)
        Toast.makeText(MyApplication.instance, intent.getStringExtra("msg"), 0).show()
        speechInit()
        speech!!.setPitch(0.5f)
        speech!!.speak(msg, TextToSpeech.QUEUE_FLUSH, null)
        return START_STICKY
    }

    private fun speechInit() {
        if (speech != null) {
            speech!!.stop()
            speech!!.shutdown()
            speech = null
        }
        speech = TextToSpeech(MyApplication.instance(), this)

    }


    override fun onDestroy() {
        super.onDestroy()
        if (speech != null)
            speech!!.stop()
        speech!!.shutdown()
//        contentResolver.unregisterContentObserver(smsObserver)
    }


    fun startServices(msg: String) {
        val intent = Intent(MyApplication.instance(), SmsService::class.java)
        intent.putExtra("msg", msg)
        startService(intent)
    }
}
