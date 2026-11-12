package com.arsam.x

import android.content.Context

object CommandExecutor {

    fun execute(
        context: Context,
        command: ParsedCommand
    ): String {

        return when (command.type) {

            CommandType.OPEN_WEBSITE -> {
                if (BrowserAgent.openWebsite(context, command.value))
                    "سایت باز شد."
                else
                    "باز کردن سایت ناموفق بود."
            }

            CommandType.OPEN_APP -> {
                val packageName = when (command.value.lowercase()) {
                    "روبیکا" -> "ir.rubika"
                    "شاد" -> "ir.medu.shad"
                    "اینستاگرام" -> "com.instagram.android"
                    "تلگرام" -> "org.telegram.messenger"
                    "واتساپ" -> "com.whatsapp"
                    "یوتیوب" -> "com.google.android.youtube"
                    else -> null
                }

                if (packageName == null) {
                    "نام این برنامه هنوز شناخته نشده است."
                } else if (AppController.openApp(context, packageName)) {
                    "برنامه باز شد."
                } else {
                    "برنامه نصب نیست یا امکان بازکردنش وجود ندارد."
                }
            }

            CommandType.CREATE_REMINDER ->
                "بخش تنظیم زمان یادآوری هنوز کامل نشده است."

            CommandType.UNKNOWN ->
                "فرمان شناخته نشد."
        }
    }
}
