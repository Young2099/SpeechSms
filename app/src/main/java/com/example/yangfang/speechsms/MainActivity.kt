package com.example.yangfang.speechsms

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.yangfang.kotlindemo.util.SharedPreferenceUtil
import com.example.yangfang.speechsms.util.RequestPermissionHelper
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener{

    private lateinit var speech: TextToSpeech
    var sp by SharedPreferenceUtil("user", "")
    private var granted: Boolean = false
    private var msg: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        granted = checkPermission()
        btn.setOnClickListener {
            checkMsg()
        }

    }

    override fun onRestart() {
        initData()

        super.onRestart()
    }

    private fun initData() {
        val intent = intent
        if (intent.getStringExtra("msg") != null)
            msg = intent.getStringExtra("msg")
    }


    private fun checkMsg() {
        if (granted && phone_text.text.isNotEmpty()) {
            //存储所要监听的电话号码
            sp = phone_text.text.toString()
//            val intent = Intent(MainActivity@ this, ObserverService::class.java)
//            startService(intent)
        } else {
            Toast.makeText(MainActivity@ this, "填写电话号码", 1).show()

        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = speech.setLanguage(Locale.CHINA)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkPermission(): Boolean {
        return RequestPermissionHelper.requestAndCheck(this, arrayOf(Manifest.permission.READ_SMS))

    }

    override fun onResume() {
        super.onResume()
        Log.e("tag", "resume")
        speech = TextToSpeech(this, this)
        sms_.text = msg
        Log.e("tag", "$msg")
        if (!speech.isSpeaking)
            speech.setPitch(0.5f)
        speech.speak(msg, TextToSpeech.QUEUE_FLUSH, null)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (permissions[0] == Manifest.permission.READ_SMS && grantResults[0] == PackageManager.PERMISSION_DENIED)
            finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        speech.stop()
        speech.shutdown()

    }

}
