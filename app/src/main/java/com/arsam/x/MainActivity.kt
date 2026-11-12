package com.arsam.x

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.EditText
import android.widget.Button

class MainActivity : Activity() {

    private lateinit var statusText: TextView
    private lateinit var inputText: EditText
    private lateinit var responseText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            createInterface()
        } catch (e: Exception) {
            statusText = TextView(this)
            statusText.text = "خطا در اجرای ARSAM X:\n${e.message}"
            setContentView(statusText)
        }
    }

    private fun createInterface() {

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(28, 40, 28, 28)
            setBackgroundColor(Color.rgb(12, 20, 38))
        }

        val title = TextView(this).apply {
            text = "ARSAM X"
            textSize = 32f
            setTextColor(Color.CYAN)
            gravity = Gravity.CENTER
        }

        statusText = TextView(this).apply {
            text = "وضعیت: برنامه اجرا شده است"
            textSize = 16f
            setTextColor(Color.GREEN)
            gravity = Gravity.CENTER
            setPadding(10, 25, 10, 25)
        }

        responseText = TextView(this).apply {
            text = "سلام! من ARSAM X هستم."
            textSize = 18f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(10, 20, 10, 20)
        }

        inputText = EditText(this).apply {
            hint = "درخواستت را بنویس..."
            setTextColor(Color.WHITE)
            setHintTextColor(Color.LTGRAY)
            textSize = 16f
        }

        val sendButton = Button(this).apply {
            text = "ارسال درخواست"
            setOnClickListener {
                val message = inputText.text.toString().trim()

                if (message.isNotEmpty()) {
                    responseText.text =
                        "درخواست دریافت شد:\n$message\n\nموتور هوش مصنوعی هنوز متصل نشده است."
                    inputText.text.clear()
                }
            }
        }

        layout.addView(title)
        layout.addView(statusText)
        layout.addView(responseText)
        layout.addView(inputText)
        layout.addView(sendButton)

        setContentView(layout)
    }
}
