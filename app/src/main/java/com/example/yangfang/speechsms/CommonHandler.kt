package com.example.yangfang.speechsms

import android.os.Handler
import android.os.Message
import android.speech.tts.TextToSpeech
import android.util.Log


class CommonHandler : Handler(){
//    override fun onInit(status: Int) {
//        if (status == TextToSpeech.SUCCESS) {
//            val result = speech.setLanguage(Locale.CHINA)
//            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Toast.makeText(MyApplication.instance, "数据丢失", Toast.LENGTH_LONG).show()
//            }
//        }
//    }


    private lateinit var speech: TextToSpeech

    companion object {
        const val SMS_RECEIVER = 100
    }

    override fun handleMessage(msg: Message?) {
        when (msg!!.what) {
            SMS_RECEIVER -> {
                val bundle = msg.data
                val msg = bundle.getString("msg")
                Log.e("HANDLER",msg)
                val service = ObserverService()
                service.startServices(msg)
            }

        }

    }

//    private fun speechVoice(msg: String) {
//        speech = TextToSpeech(MyApplication.instance, this)
//        if (!speech.isSpeaking)
//            speech.setPitch(0.5f)
//        speech.speak(msg, TextToSpeech.QUEUE_FLUSH, null)
//    }
}