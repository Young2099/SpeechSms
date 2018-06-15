package com.example.yangfang.speechsms.util

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

class RequestPermissionHelper {

    companion object {
        fun requestAndCheck(activity: AppCompatActivity, permissionsNeed: Array<String>): Boolean {
            //检查权限
            val permissionNeedRequest = ArrayList<String>()
            for (item in permissionsNeed) {
                if (ContextCompat.checkSelfPermission(activity, item) == PackageManager.PERMISSION_GRANTED) {
                    continue
                }
                permissionNeedRequest.add(item)
            }
            var isFlag = false
            //请求权限
            if (permissionNeedRequest.size == 0) {
                isFlag = true
            } else {
                var permissions = Array(permissionNeedRequest.size, { i -> permissionNeedRequest[i] })
                permissions = permissionNeedRequest.toArray(permissions)
                ActivityCompat.requestPermissions(activity, permissions, 0)
                isFlag = false
            }
            return isFlag
        }
    }
}
