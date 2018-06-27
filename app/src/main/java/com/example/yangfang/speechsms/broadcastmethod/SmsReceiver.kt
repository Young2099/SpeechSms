package com.example.yangfang.speechsms.broadcastmethod

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.telephony.SmsMessage
import android.util.Log
import com.example.yangfang.kotlindemo.util.SharedPreferenceUtil

class SmsReceiver : BroadcastReceiver() {

    companion object {
        const val SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED"
        const val SMS_DELIVER_ACTION = "android.provider.Telephony.SMS_DELIVER"
    }

    private var phone by SharedPreferenceUtil("user", "")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        Log.e("OnReceiver", phone)
        val action = intent.action
        if (SMS_RECEIVED_ACTION == action || SMS_DELIVER_ACTION == action) {
            val bundle = intent.extras
            val format = intent.getStringExtra("format")
            if (bundle != null) {
                //读取短信，根据大小会把短信内容分成一段一段
                //puds即是一条短信为140个字符的英文长度
                val pdus = bundle.get("pdus") as Array<*>
                if (pdus.isNotEmpty()) {
                    val messages = arrayOfNulls<SmsMessage>(pdus.size)
                    for (i in pdus.indices) {
                        val sms = pdus[i] as ByteArray
                        messages[i] = SmsMessage.createFromPdu(sms, format)
                    }
                    Log.e("smsReceiver", "条数：${messages.size}")
                    var content = String()
                    for (message in messages) {
                        val sender = message!!.originatingAddress// 得到发信息的号码
//                        if (sender.equals(phone)) {
                        content += message.messageBody// 得到短信内容

                        Log.e("SmsReceiver", "内容1：$content ")
                        Log.e("SmsReceiver", "电话号码：$sender ")
//                        }
                    }
                    /**
                     * 启动服务播放语音
                     */
//                    if (content.isNotEmpty()) {
                    val intent = Intent(context, SmsService::class.java)
                    intent.putExtra("msg", content)
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                    context.startService(intent)
//                    }
                }
            }
        }
    }

}