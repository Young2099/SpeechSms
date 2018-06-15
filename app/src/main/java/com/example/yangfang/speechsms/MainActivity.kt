package com.example.yangfang.speechsms

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.yangfang.kotlindemo.util.SharedPreferenceUtil
import com.example.yangfang.speechsms.util.RequestPermissionHelper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var sp by SharedPreferenceUtil("user", "")
    private var granted: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        granted = checkPermission()
        btn.setOnClickListener {
            startPhoneAndBroadCast()

        }
        /**
         * test 测试语音播放是否正常
         */
        btn_speech.setOnClickListener {
            Log.e("...", "sppech")
        }

    }

    /**
     * 检查number启动广播
     */
    private fun startPhoneAndBroadCast() {
        if (granted && phone_text.text.isNotEmpty()) {
            //存储所要监听的电话号码
            sp = phone_text.text.toString()
//            val intent = Intent(MainActivity@ this, ObserverService::class.java)
//            startService(intent)
            val intent = Intent("com.example.yangfang.speechsms.SmsReceiver")

            intent.putExtra("phone", phone_text.text.toString())
            sendBroadcast(intent)
        } else {
            Toast.makeText(MainActivity@ this, "填写电话号码", 1).show()

        }
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


}
