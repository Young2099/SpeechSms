package com.example.yangfang.speechsms

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.yangfang.kotlindemo.util.SharedPreferenceUtil
import com.example.yangfang.speechsms.contentobserver.SmsService1
import com.example.yangfang.speechsms.util.RequestPermissionHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**
     * sharedPreferences保存号码
     */
    var sp by SharedPreferenceUtil("user", "")
    var smsSp by SharedPreferenceUtil("sms", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        registerBroad()
        //权限申请
        checkPermission()
        btn.setOnClickListener {
            startPhoneAndBroadCast()
        }
        btn_stop.setOnClickListener {
            //广播方法
//            unregisterReceiver(SmsReceiver())
            //停止服务
            stopService(Intent(MainActivity@ this, SmsService1::class.java))

        }

    }

    /**
     * 注册广播
     */
    private fun registerBroad() {
        val smsReceiver = SmsReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.yangfang.speechsms.SmsReceiver")
        registerReceiver(smsReceiver, intentFilter)
    }

    /**
     * 检查number发送服务或者广播
     */
    private fun startPhoneAndBroadCast() {
        if (checkPermission() && phone_text.text.isNotEmpty()) {
            //sharedPreference存储所要监听的电话号码
            sp = phone_text.text.toString()
//            startBroadCast()
            startServices()
        } else {
            Toast.makeText(MainActivity@ this, "填写电话号码", 1).show()

        }
    }

    /**
     * 启动服务，数据观察者监听短信消息
     */
    private fun startServices() {
        smsSp = ""//重置存储短信的值
        val intent = Intent(this, SmsService1::class.java)
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
        startService(intent)
    }

    /**
     * 广播直接监听短信数据，再启动服务播放语音
     */
    private fun startBroadCast() {
        val intent = Intent()
        intent.action = "com.example.yangfang.speechsms.SmsReceiver"
        intent.putExtra("phone", phone_text.text.toString())
        sendBroadcast(intent)
    }


    private fun checkPermission(): Boolean {
        return RequestPermissionHelper.requestAndCheck(this, arrayOf(Manifest.permission.READ_SMS))

    }

    override fun onResume() {
        super.onResume()
        Log.e("tag", "resume")

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (permissions[0] == Manifest.permission.READ_SMS && grantResults[0] == PackageManager.PERMISSION_DENIED)
            finish()
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(SmsReceiver())
    }


}
