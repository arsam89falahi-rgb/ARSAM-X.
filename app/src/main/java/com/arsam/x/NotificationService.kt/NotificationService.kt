package com.arsam.x

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class NotificationService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notification = sbn.notification
        val extras = notification.extras

        val title = extras.getCharSequence("android.title")?.toString()
            ?: "اعلان جدید"

        val message = extras.getCharSequence("android.text")?.toString()
            ?: return

        val packageName = sbn.packageName

        val allowedApps = setOf(
            "ir.rubika",
            "ir.medu.shad"
        )

        if (packageName in allowedApps) {
            Log.d(
                "ARSAM_X",
                "برنامه: $packageName | فرستنده: $title | پیام: $message"
            )
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        Log.d("ARSAM_X", "اعلان حذف شد")
    }
}
