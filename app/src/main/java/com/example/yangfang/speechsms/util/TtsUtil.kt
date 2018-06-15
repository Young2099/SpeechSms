package com.example.yangfang.speechsms.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import java.util.*

object TtsUtil {
    private var mTts: TextToSpeech? = null
    fun read(ctx: Context, content: String) {
//        if (Settings.Secure.getInt(ctx.contentResolver, "hands_free_mode",
//                        0) == 0) {
//            return
//        }
        if (null != mTts) {
            mTts!!.stop()
            try {
                mTts!!.shutdown()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        mTts = TextToSpeech(ctx, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {

                if (null != mTts) {
                    mTts!!.setSpeechRate(1.0f)
                    mTts!!.language = Locale.CHINA
                    mTts!!.speak(content, TextToSpeech.QUEUE_FLUSH, null)
                    mTts!!.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onDone(utteranceId: String) {
                            mTts!!.stop()
                            mTts!!.shutdown()
                        }

                        override fun onError(utteranceId: String) {
                            mTts!!.stop()
                            mTts!!.shutdown()
                        }

                        override fun onStart(utteranceId: String) {}
                    })
                } else {
                    Log.e("TextToSpeech", "Cann't create TextToSpeech object")
                }
            }
        })
    }
}
