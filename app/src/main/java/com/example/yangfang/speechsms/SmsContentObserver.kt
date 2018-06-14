package com.example.yangfang.speechsms

import android.database.ContentObserver
import android.os.Handler
import android.util.Log


class SmsContentObserver(mHandler: CommonHandler) : ContentObserver(mHandler) {

    constructor(observer: ObserverService, mHandler: CommonHandler) : this(mHandler) {
        this.mObserver = observer
        this.mHandler = mHandler
    }


    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        Log.e("change","smsOnchange")
//        getSmsCode()
    }

    private lateinit var mHandler: Handler
    private lateinit var mObserver: ObserverService

//
//    private fun getSmsCode() {
//        var cursor: Cursor? = null
//        try {
//            //读取特定短信，第1条
//            cursor = mObserver.contentResolver.query(ObserverService.SMS_INBOX, arrayOf("_id", "address", "person", "body", "read", "date"), null, null, "date desc")
//            var smsBody = ""
//            if (null != cursor) {
//                val smsNumber: String
//                if (cursor.moveToFirst()) {
//                    smsBody = cursor.getString(cursor.getColumnIndex("body"))
//                    smsNumber = cursor.getString(cursor.getColumnIndex("address"))
//                    Log.e("SmsObserver","number$smsNumber: $smsBody")
//                }
//            }
//
//            val msg = Message()
//            msg.what = CommonHandler.SMS_RECEIVER
//            val bundle = Bundle()
//            if(smsBody.isNotEmpty())
//            bundle.putString("msg", smsBody)
//            msg.data = bundle
//            mHandler.sendMessage(msg)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            if (cursor != null) {
//                cursor.close()
//            }
//        }
//    }
}