package com.arsam.x

import android.app.*
import android.os.*
import android.content.*
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.widget.*
import android.graphics.Color
import java.util.*

class MainActivity : Activity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var chat: LinearLayout
    private lateinit var input: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(this, this)

        val root = LinearLayout(this)
        root.orientation = LinearLayout.VERTICAL
        root.setPadding(20, 25, 20, 20)
        root.setBackgroundColor(Color.rgb(10, 11, 15))

        val title = TextView(this)
        title.text = "ARSAM X"
        title.textSize = 28f
        title.setTextColor(Color.rgb(240, 195, 75))
        title.gravity = 17
        root.addView(title)

        chat = LinearLayout(this)
        chat.orientation = LinearLayout.VERTICAL

        val scroll = ScrollView(this)
        scroll.addView(chat)
        root.addView(scroll, LinearLayout.LayoutParams(-1, 0, 1f))

        input = EditText(this)
        input.hint = "پیامت را بنویس"
        root.addView(input)

        val send = Button(this)
        send.text = "ارسال"
        send.setOnClickListener {
            val text = input.text.toString()
            if (text.isNotBlank()) {
                addMessage("تو: $text")
                val response = answer(text)
                addMessage("ARSAM X: $response")
                input.text.clear()
                tts.speak(response, TextToSpeech.QUEUE_FLUSH, null, "arsam")
            }
        }
        root.addView(send)

        val voice = Button(this)
        voice.text = "🎙 فرمان صوتی"
        voice.setOnClickListener { startVoice() }
        root.addView(voice)

        val alarm = Button(this)
        alarm.text = "⏰ تنظیم آلارم ساعت ۷"
        alarm.setOnClickListener {
            val intent = Intent(android.provider.AlarmClock.ACTION_SET_ALARM)
            intent.putExtra(android.provider.AlarmClock.EXTRA_HOUR, 7)
            intent.putExtra(android.provider.AlarmClock.EXTRA_MINUTES, 0)
            startActivity(intent)
        }
        root.addView(alarm)

        setContentView(root)
        addMessage("سلام! من ARSAM X هستم.")
    }

    private fun addMessage(text: String) {
        val view = TextView(this)
        view.text = text
        view.textSize = 16f
        view.setTextColor(Color.WHITE)
        view.setPadding(12, 14, 12, 14)
        chat.addView(view)
    }

    private fun answer(text: String): String {
        val t = text.lowercase(Locale.ROOT)
        return when {
            t.contains("سلام") -> "سلام! آماده‌ام کمکت کنم."
            t.contains("اسمت") -> "من ARSAM X هستم."
            t.contains("جوک") -> "چرا کامپیوتر مریض شد؟ چون ویروس گرفته بود!"
            t.contains("خداحافظ") -> "خداحافظ!"
            else -> "این قابلیت هنوز اضافه نشده است."
        }
    }

    private fun startVoice() {
        try {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa-IR")
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            startActivityForResult(intent, 100)
        } catch (e: Exception) {
            addMessage("تشخیص گفتار در دسترس نیست.")
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val result = data?.getStringArrayListExtra(
                RecognizerIntent.EXTRA_RESULTS
            )
            val text = result?.firstOrNull() ?: return
            addMessage("تو: $text")
            val response = answer(text)
            addMessage("ARSAM X: $response")
            tts.speak(response, TextToSpeech.QUEUE_FLUSH, null, "arsam")
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("fa", "IR")
        }
    }

    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }
}
