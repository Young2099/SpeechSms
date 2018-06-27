package com.example.yangfang.speechsms.tts

import android.content.Context

object TtsWarpper {
    fun with(context: Context): TtsContract {
       return TtsContract(TtsSource(context))
    }

}