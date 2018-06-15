package com.example.yangfang.speechsms

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    companion object {
        const val SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED"
        const val SMS_DELIVER_ACTION = "android.provider.Telephony.SMS_DELIVER"
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (SMS_RECEIVED_ACTION == action || SMS_DELIVER_ACTION == action) {
            val bundle = intent.extras
            val format = intent.getStringExtra("format")
            if (bundle != null) {
                //读取短信，根据大小会把短信内容分成一段一段
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
                        content += message!!.messageBody// 得到短信内容
                        val sender = message.originatingAddress// 得到发信息的号码
                        Log.e("SmsReceiver", "内容1：$content ")
                        Log.e("SmsReceiver", "电话号码：$sender ")
                    }

                    val intent = Intent(context, SmsService::class.java)
                    intent.putExtra("msg", content)
                    intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    context.startService(intent)
                }
            }
        }
    }

}