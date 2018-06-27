package com.example.yangfang.speechsms.tts

import android.content.Context

object TtsWarpper {
    fun with(context: Context): TtsXunfei {
       return TtsXunfei(TtsSource(context))
    }

}