package com.arsam.x

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

object NotificationStore {
    @Volatile
    var latestMessage: String = ""
}

class NotificationService : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val extras = sbn.notification.extras

        val title = extras
            .getCharSequence("android.title")
            ?.toString()
            .orEmpty()

        val text = extras
            .getCharSequence("android.text")
            ?.toString()
            .orEmpty()

        if (title.isNotBlank() || text.isNotBlank()) {
            NotificationStore.latestMessage =
                "برنامه: ${sbn.packageName}\nعنوان: $title\nپیام: $text"
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        // اعلان حذف‌شده نادیده گرفته می‌شود.
    }
}
