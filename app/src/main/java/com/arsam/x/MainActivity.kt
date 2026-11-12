package com.arsam.x

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.view.Gravity
import android.widget.*

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setPadding(24, 40, 24, 40)
        layout.setBackgroundColor(Color.rgb(12, 20, 38))

        val title = TextView(this)
        title.text = "ARSAM X"
        title.textSize = 32f
        title.setTextColor(Color.CYAN)
        title.gravity = Gravity.CENTER

        val status = TextView(this)
        status.text = "برنامه با موفقیت اجرا شد"
        status.textSize = 18f
        status.setTextColor(Color.GREEN)
        status.gravity = Gravity.CENTER
        status.setPadding(10, 30, 10, 30)

        val input = EditText(this)
        input.hint = "درخواستت را بنویس"
        input.setTextColor(Color.WHITE)
        input.setHintTextColor(Color.LTGRAY)

        val button = Button(this)
        button.text = "ارسال درخواست"

        val response = TextView(this)
        response.text = "سلام! من ARSAM X هستم."
        response.textSize = 18f
        response.setTextColor(Color.WHITE)

        button.setOnClickListener {
            val message = input.text.toString().trim()

            if (message.isNotEmpty()) {
                response.text = "درخواست دریافت شد:\n$message"
                input.text.clear()
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
