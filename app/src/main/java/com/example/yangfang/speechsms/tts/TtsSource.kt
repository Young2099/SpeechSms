package com.example.yangfang.speechsms.tts

import android.content.Context

class TtsSource(context: Context) : Source() {
    var mContext = context


    override fun getContext(): Context {
        return mContext
    }


}