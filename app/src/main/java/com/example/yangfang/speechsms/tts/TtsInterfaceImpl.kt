package com.example.yangfang.speechsms.tts

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.iflytek.cloud.*


class TtsInterfaceImpl(val source: TtsSource) : TtsInterface, SynthesizerListener {


    // 默认发音人
    private val voicer = "xiaoyan"

    // 引擎类型
    private val mEngineType = SpeechConstant.TYPE_CLOUD

    private lateinit var mTts: SpeechSynthesizer
    var speechText = ""

    override fun set(text: String): TtsInterface {
        speechText = text
        return this
    }

    private fun setParam() {
        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null)
        if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            // 根据合成引擎设置相应参数
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer)
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, "50")
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, "50")
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, "50")
        } else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL)
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "")
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */

        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3")
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true")

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
        val ts = Environment.getExternalStorageDirectory().absolutePath + "/msc/tts.wav"
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, ts)
    }

    override fun create(): TtsInterface {
        mTts = SpeechSynthesizer.createSynthesizer(source.mContext, {
            Log.d("TAG", "InitListener init() code = $it")
            if (it != ErrorCode.SUCCESS) {
                Log.e("TtsSource", "初始化失败,错误码：$it")
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里

            }

        })
        return this
    }

    override fun destroy() {
        mTts.stopSpeaking()
        // 退出时释放连接
        mTts.destroy()
    }

    override fun onCompleted(error: SpeechError?) {
        if (error == null) {
            Toast.makeText(source.mContext, "播放完成", 0).show()
        } else {
            Toast.makeText(source.mContext, error.getPlainDescription(true), 0).show()
        }
    }

    override fun onEvent(eventType: Int, p1: Int, p2: Int, obj: Bundle?) {
        if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            val sid = obj!!.getString(SpeechEvent.KEY_EVENT_SESSION_ID)
            Log.d("TAG", "session id =$sid")
        }
    }

    override fun onBufferProgress(p0: Int, p1: Int, p2: Int, p3: String?) {
    }

    override fun onSpeakBegin() {
    }

    override fun onSpeakProgress(p0: Int, p1: Int, p2: Int) {
    }

    override fun onSpeakPaused() {
    }

    override fun onSpeakResumed() {
    }

    override fun start() {
        if (speechText.isNotEmpty()) {
            setParam()
            val code = mTts.startSpeaking(speechText, this)
            Log.e("TtsInterfaceImpl", "$code")
            if (code != ErrorCode.SUCCESS) {
                Log.e("TtsInterfaceImpl", "语音合成错误码$code")
            }
        }
    }


}