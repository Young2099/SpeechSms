package com.example.yangfang.speechsms.tts

interface TtsInterface {

    /**
     * 开始发音
     */
    fun speech()

    /**
     * 设置文字内容
     */
    fun set(text: String): TtsInterface

    /**
     * destroy
     */
    fun destroy()

    /**
     * init param
     */
    fun create(): TtsInterface

}