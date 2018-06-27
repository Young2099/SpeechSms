package com.example.yangfang.speechsms.tts

class TtsContract(val source: TtsSource) {

    companion object {
        private var FACTORY: TtsActionFactory = TtsFactoryImpl()
    }

    fun init(): TtsInterface {
        return FACTORY.create(source)
    }


    interface TtsActionFactory {

        fun create(source: TtsSource): TtsInterface
    }

}
