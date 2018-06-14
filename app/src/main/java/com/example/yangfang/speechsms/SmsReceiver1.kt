package com.example.yangfang.speechsms

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver1 : BroadcastReceiver() {

    companion object {
        const val SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED"
        const val SMS_DELIVER_ACTION = "android.provider.Telephony.SMS_DELIVER"
    }

    private var mContext: Context? = null

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SimpleDateFormat")
    override fun onReceive(context: Context, intent: Intent) {
        mContext = context
        val action = intent.action
        if (SMS_RECEIVED_ACTION == action || SMS_DELIVER_ACTION == action) {
            val bundle = intent.extras
            val format = intent.getStringExtra("format")
            if (bundle != null) {
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
//                        val date = Date(message.timestampMillis)
//                        val sendContent = format.format(date) + ":" + sender + "--" + content
//                        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//                        format.timeZone = TimeZone.getTimeZone("GMT+08:00")
//                        Log.e("SmsReceiver", "内容2：$sendContent ")
                    }

                    val intent = Intent(context, ObserverService::class.java)
                    intent.putExtra("msg", content)
                    context.startActivity(intent)
                }
            }
        }
    }

}