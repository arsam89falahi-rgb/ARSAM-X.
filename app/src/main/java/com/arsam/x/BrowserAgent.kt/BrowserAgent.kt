package com.arsam.x

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object BrowserAgent {

    fun openWebsite(context: Context, address: String): Boolean {
        try {
            var url = address.trim()

            if (url.isEmpty()) {
                Toast.makeText(
                    context,
                    "آدرس سایت خالی است",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }

            if (!url.startsWith("http://") &&
                !url.startsWith("https://")) {
                url = "https://$url"
            }

            val uri = Uri.parse(url)

            if (uri.host.isNullOrEmpty() ||
                (uri.scheme != "http" && uri.scheme != "https")) {
                Toast.makeText(
                    context,
                    "آدرس سایت معتبر نیست",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }

            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)

            return true

        } catch (e: Exception) {
            Toast.makeText(
                context,
                "باز کردن سایت ناموفق بود",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
    }
}
