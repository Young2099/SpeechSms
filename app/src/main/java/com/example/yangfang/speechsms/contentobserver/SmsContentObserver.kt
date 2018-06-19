package com.example.yangfang.speechsms.contentobserver

import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.os.Handler
import android.util.Log
import com.example.yangfang.kotlindemo.util.SharedPreferenceUtil
import com.example.yangfang.speechsms.CommonHandler
import com.example.yangfang.speechsms.util.TtsUtil

/**
 * 数据库监听短信
 */
class SmsContentObserver(mHandler: CommonHandler) : ContentObserver(mHandler) {
    /**
     * phone
     */
    val sp by SharedPreferenceUtil("user", "")
    var spSms by SharedPreferenceUtil("sms", "")

    constructor(context: Context, mHandler: CommonHandler) : this(mHandler) {
        this.mContext = context
        this.mHandler = mHandler
    }

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        Log.e("change", "smsOnchange")
        getSmsCode()
    }

    private lateinit var mHandler: Handler
    private lateinit var mContext: Context

    /**
     * 读取短信
     */
    private fun getSmsCode() {
        var cursor: Cursor? = null
        try {
            //读取特定短信，第1条
            cursor = mContext.contentResolver.query(SmsService1.SMS_INBOX, arrayOf("_id", "address", "person", "body", "read", "date"), null, null, "date desc")
            var smsBody = ""
            if (null != cursor) {
                val smsNumber: String
                if (cursor.moveToFirst()) {
                    smsBody = cursor.getString(cursor.getColumnIndex("body"))
                    smsNumber = cursor.getString(cursor.getColumnIndex("address"))
                    Log.e("SmsObserver", "number$smsNumber: $smsBody")
//                    if (smsNumber == sp) {
                        //TODO 监控指定的电话号码
//                    }
                    if (spSms.isEmpty()) {
                        //第一次会读取到之前的电话号码的短信消息
                        spSms = smsBody
                    }else{
                        TtsUtil.read(mContext, smsBody)
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }
}