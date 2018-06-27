package com.example.yangfang.speechsms.util

import android.app.Activity
import android.content.Context
import android.widget.Toast

fun Activity.toast(context: Context, text:String){
    Toast.makeText(context,text, Toast.LENGTH_LONG).show()
}

