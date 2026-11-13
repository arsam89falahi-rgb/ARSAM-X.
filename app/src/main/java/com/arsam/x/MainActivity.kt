package com.arsam.x

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(24, 40, 24, 40)
            setBackgroundColor(Color.rgb(12, 20, 38))
        }

        val title = TextView(this).apply {
            text = "ARSAM X"
            textSize = 32f
            setTextColor(Color.CYAN)
            gravity = Gravity.CENTER
        }

        val status = TextView(this).apply {
            text = "برنامه با موفقیت اجرا شد"
            textSize = 18f
            setTextColor(Color.GREEN)
            gravity = Gravity.CENTER
            setPadding(10, 30, 10, 30)
        }

        val input = EditText(this).apply {
            hint = "درخواستت را بنویس"
            setTextColor(Color.WHITE)
            setHintTextColor(Color.LTGRAY)
        }

        val response = TextView(this).apply {
            text = "سلام! من ARSAM X هستم."
            textSize = 18f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            setPadding(10, 20, 10, 20)
        }

        val button = Button(this).apply {
            text = "ارسال درخواست"
            setOnClickListener {
                val message = input.text.toString().trim()

                if (message.isNotEmpty()) {
                    response.text =
                        "درخواست دریافت شد:\n$message\n\nموتور هوش مصنوعی هنوز متصل نشده است."
                    input.text.clear()
                } else {
                    response.text = "لطفاً درخواستت را بنویس."
                }
            }
        }

        layout.addView(title)
        layout.addView(status)
        layout.addView(input)
        layout.addView(button)
        layout.addView(response)

        setContentView(layout)
    }
}
