package com.example.yangfang.speechsms.contentobserver

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.yangfang.kotlindemo.util.SharedPreferenceUtil
import com.example.yangfang.speechsms.CommonHandler
import com.example.yangfang.speechsms.app.MyApplication

class SmsService1 : Service() {

    companion object {
        val SMS_INBOX: Uri = Uri.parse("content://sms/inbox")

    }

    private lateinit var smsObserver: SmsContentObserver

    private var getPhone by SharedPreferenceUtil("user", "")
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Toast.makeText(MyApplication.instance!!, "服务启动", 0).show()
        smsObserver = SmsContentObserver(this, CommonHandler())
        contentResolver.registerContentObserver(SMS_INBOX, true, smsObserver)
        Log.e("OnCreate", "onCreate:$getPhone")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("onStartCommand:", "onStartCommand")
        contentResolver.notifyChange(SMS_INBOX, null)
        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        contentResolver.unregisterContentObserver(smsObserver)
    }


}

