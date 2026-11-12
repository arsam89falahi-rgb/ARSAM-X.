package com.arsam.x

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import android.widget.Button
import android.widget.ScrollView

class MainActivity : Activity() {

    private lateinit var statusText: TextView
    private lateinit var inputText: EditText
    private lateinit var responseText: TextView
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createInterface()
    }

    private fun createInterface() {

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 35, 24, 25)
            setBackgroundColor(Color.rgb(12, 20, 38))
        }

        val title = TextView(this).apply {
            text = "ARSAM X"
            textSize = 32f
            setTextColor(Color.CYAN)
            gravity = Gravity.CENTER
        }

        statusText = TextView(this).apply {
            text = "وضعیت: آماده دریافت درخواست"
            textSize = 16f
            setTextColor(Color.GREEN)
            gravity = Gravity.CENTER
            setPadding(10, 20, 10, 20)
        }

        responseText = TextView(this).apply {
            text = "سلام! من ARSAM X هستم."
            textSize = 18f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(10, 20, 10, 20)
        }

        val scrollView = ScrollView(this).apply {
            addView(responseText)
        }

        inputText = EditText(this).apply {
            hint = "درخواستت را بنویس..."
            setTextColor(Color.WHITE)
            setHintTextColor(Color.LTGRAY)
            textSize = 16f
        }

        sendButton = Button(this).apply {
            text = "ارسال درخواست"

            setOnClickListener {
                sendMessage()
            }
        }

        layout.addView(title)
        layout.addView(statusText)
        layout.addView(
            scrollView,
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                0,
                1f
            )
        )
        layout.addView(inputText)
        layout.addView(sendButton)

        setContentView(layout)
    }

    private fun sendMessage() {

        val message = inputText.text.toString().trim()

        if (message.isEmpty()) return

        inputText.text.clear()
        sendButton.isEnabled = false

        statusText.text = "در حال پردازش درخواست..."
        responseText.text = "در حال دریافت پاسخ..."

        AIService.sendMessage(message) { reply ->

            responseText.text = reply
            statusText.text = "وضعیت: آماده"
            sendButton.isEnabled = true
        }
    }
}
