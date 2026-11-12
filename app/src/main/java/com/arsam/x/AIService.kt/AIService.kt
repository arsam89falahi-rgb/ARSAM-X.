package com.arsam.x

import android.os.Handler
import android.os.Looper
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object AIService {

    private const val SERVER_URL = "https://YOUR_BACKEND_URL/chat"

    private val mainHandler = Handler(Looper.getMainLooper())

    fun sendMessage(
        message: String,
        callback: (String) -> Unit
    ) {
        if (message.isBlank()) {
            callback("لطفاً یک پیام بنویس.")
            return
        }

        thread {
            var result: String

            try {
                val connection =
                    URL(SERVER_URL).openConnection() as HttpURLConnection

                connection.apply {
                    requestMethod = "POST"
                    connectTimeout = 15000
                    readTimeout = 30000
                    doOutput = true
                    setRequestProperty(
                        "Content-Type",
                        "application/json; charset=UTF-8"
                    )
                    setRequestProperty("Accept", "application/json")
                }

                try {
                    val request = JSONObject()
                        .put("message", message)
                        .toString()

                    connection.outputStream.use { output ->
                        output.write(request.toByteArray(Charsets.UTF_8))
                    }

                    val responseCode = connection.responseCode

                    val stream = if (responseCode in 200..299) {
                        connection.inputStream
                    } else {
                        connection.errorStream
                    }

                    val response = stream?.bufferedReader()
                        ?.use { it.readText() }
                        ?: ""

                    result = if (responseCode in 200..299) {
                        JSONObject(response)
                            .optString("reply", "پاسخی دریافت نشد.")
                    } else {
                        "خطای سرور: $responseCode"
                    }

                } finally {
                    connection.disconnect()
                }

            } catch (e: Exception) {
                result = "خطا در ارتباط با سرور: ${e.message}"
            }

            mainHandler.post {
                callback(result)
            }
        }
    }
}
