package com.arsam.x

import android.content.Context

object CommandExecutor {

    fun execute(
        context: Context,
        command: ParsedCommand
    ): String {

        return when (command.type) {

            CommandType.OPEN_WEBSITE -> {
                val success = BrowserAgent.openWebsite(
                    context,
                    command.value
                )

                if (success) {
                    "سایت باز شد."
                } else {
                    "باز کردن سایت ناموفق بود."
                }
            }

            CommandType.OPEN_APP -> {
                "برای باز کردن این برنامه هنوز باید نام آن به بسته اندروید متصل شود: ${command.value}"
            }

            CommandType.CREATE_REMINDER -> {
                "برای یادآوری هنوز باید زمان و متن یادآوری مشخص و تأیید شود."
            }

            CommandType.UNKNOWN -> {
                "هنوز فرمان را نمی‌شناسم. درخواستت را دقیق‌تر بنویس."
            }
        }
    }
}
