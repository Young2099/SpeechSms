package com.example.yangfang.speechsms.tts

class TtsFactoryImpl :TtsContract.TtsActionFactory{
    override fun create(source: TtsSource): TtsInterface {
        return TtsInterfaceImpl(source)
    }

}