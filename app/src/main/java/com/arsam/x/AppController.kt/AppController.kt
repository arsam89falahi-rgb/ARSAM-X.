package com.arsam.x

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

class AppController(private val context: Context) {

    fun openApp(packageName: String): Boolean {
        return try {
            val intent = context.packageManager
                .getLaunchIntentForPackage(packageName)

            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }

    fun isAppInstalled(packageName: String): Boolean {
        return try {
            context.packageManager
                .getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
