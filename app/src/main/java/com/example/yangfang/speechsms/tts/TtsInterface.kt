package com.example.yangfang.speechsms.tts

interface TtsInterface {

    fun start()
    fun set(text: String): TtsInterface
    fun destroy()
    fun create(): TtsInterface

}