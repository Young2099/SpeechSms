package com.example.yangfang.speechsms

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.yangfang.kotlindemo.util.SharedPreferenceUtil
import com.example.yangfang.speechsms.broadcastmethod.SmsReceiver
import com.example.yangfang.speechsms.contentobserver.SmsService1
import com.example.yangfang.speechsms.tts.TtsWarpper
import com.example.yangfang.speechsms.util.toast
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.Permission
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    /**
     * sharedPreferences保存电话号码
     */
    var sp by SharedPreferenceUtil("user", "")
    private var smsReceiver: SmsReceiver? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 初始化合成对象
        //权限申请
        grantedPermission()
        btn.setOnClickListener(this)
        btn_stop.setOnClickListener(this)
        play.visibility = View.GONE


    }

    private fun grantedPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_SMS, Permission.RECORD_AUDIO, Permission.READ_SMS)
                .onGranted { _ -> toast(this, "短信权限开启") }
                .onDenied { _ -> toast(this, "短信权限禁止") }
                .start()

    }


    override fun onClick(it: View) {
        when (it.id) {
            R.id.btn_stop -> {
                //停止注册广播
                if (smsReceiver != null) {
                    unregisterReceiver(smsReceiver!!)
                    Toast.makeText(MainActivity@ this, "取消监听短信", Toast.LENGTH_LONG).show()
                    smsReceiver = null
                }
            }
            R.id.btn -> {
                startPhoneAndBroadCast()
            }
        }
    }

    /**
     * 检查phone发送服务或者广播
     */
    private fun startPhoneAndBroadCast() {
        if (phone_text.text.isNotEmpty()) {
            //动态注册广播
            registerBroad()
            sp = phone_text.text.toString()
            sendBroadCast()
//            startServices()
            toast(MainActivity@ this, "启动短信监听")
        } else {
            toast(MainActivity@ this, "填写需要监听的电话号码")

        }
    }

    /**
     * 启动服务，数据观察者监听短信消息
     */
    private fun startServices() {
        val intent = Intent(this, SmsService1::class.java)
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        startService(intent)
    }

    /**
     * 动态注册广播
     */
    private fun registerBroad() {
        if (smsReceiver == null) {
            smsReceiver = SmsReceiver()
            val intentFilter = IntentFilter()
            intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED")
            intentFilter.addAction("android.provider.Telephony.SMS_DELIVER")
            intentFilter.addAction("com.example.yangfang.speechsms.SmsReceiver")
            intentFilter.addCategory("android.intent.category.DEFAULT")
            registerReceiver(smsReceiver!!, intentFilter)
            Toast.makeText(this, "短信监听开启", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "短信监听已经开启", Toast.LENGTH_LONG).show()
        }

    }


    /**
     * 发送广播直接监听短信数据，再启动服务播放语音
     */
    private fun sendBroadCast() {
        val intent = Intent()
        intent.action = "com.example.yangfang.speechsms.SmsReceiver"
        intent.putExtra("phone", phone_text.text.toString())
        sendBroadcast(intent)
    }


    override fun onResume() {
        super.onResume()
        Log.e("tag", "resume")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("MainActivity", "onDestroy()")
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver!!)
            smsReceiver = null
        }
        TtsWarpper.with(this)
                .init()
                .destroy()

    }


}


