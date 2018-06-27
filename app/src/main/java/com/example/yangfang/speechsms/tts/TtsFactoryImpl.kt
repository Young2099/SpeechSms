package com.example.yangfang.speechsms.tts

class TtsFactoryImpl :TtsXunfei.TtsActionFactory{
    override fun create(source: TtsSource): TtsInterface {
        return TtsInterfaceImpl(source)
    }

}