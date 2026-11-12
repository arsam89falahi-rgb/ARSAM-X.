package com.arsam.x

import android.content.Context
import android.content.Intent

object AppController {

    fun openApp(
        context: Context,
        packageName: String
    ): Boolean {
        return try {
            val intent = context.packageManager
                .getLaunchIntentForPackage(packageName)
                ?: return false

            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun isAppInstalled(
        context: Context,
        packageName: String
    ): Boolean {
        return try {
            context.packageManager.getApplicationInfo(
                packageName,
                0
            )
            true
        } catch (e: Exception) {
            false
        }
    }
}
