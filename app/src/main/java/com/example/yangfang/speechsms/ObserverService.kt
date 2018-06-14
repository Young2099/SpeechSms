package com.example.yangfang.speechsms

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.example.yangfang.kotlindemo.util.SharedPreferenceUtil

class ObserverService : Service(){

    var getPhone by SharedPreferenceUtil("user", "")
//    private lateinit var smsObserver: SmsContentObserver
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

//    companion object {
//
//        val SMS_INBOX: Uri = Uri.parse("content://sms//inbox")
//
//    }


    override fun onCreate() {
        super.onCreate()
        Toast.makeText(MyApplication.instance!!, "服务启动", 0).show()
        Log.e("服务" ,"onCreate:$getPhone")
//        smsObserver = SmsContentObserver(this, CommonHandler())
//        contentResolver.registerContentObserver(SMS_INBOX, true, smsObserver)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("服务","onStartCommand")
//        contentResolver.notifyChange(SMS_INBOX, null)
        Toast.makeText(MyApplication.instance, intent!!.getStringExtra("msg"),0).show()
        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
//        contentResolver.unregisterContentObserver(smsObserver)
    }


    fun startServices(msg: String) {
        val intent = Intent(MyApplication.instance(), ObserverService::class.java)
        intent.putExtra("msg", msg)
        startService(intent)
    }
}
