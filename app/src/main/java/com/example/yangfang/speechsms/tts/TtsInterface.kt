package com.example.yangfang.speechsms.tts

interface TtsInterface {

    fun speech()
    fun set(text: String): TtsInterface
    fun destroy()
    fun create(): TtsInterface

}