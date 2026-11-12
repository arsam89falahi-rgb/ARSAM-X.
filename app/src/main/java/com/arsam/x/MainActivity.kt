package com.arsam.x

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.RecognitionListener
import android.speech.tts.TextToSpeech
import android.view.Gravity
import android.view.View
import android.widget.*
import java.util.Locale

class MainActivity : Activity(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var output: TextView
    private lateinit var input: EditText
    private lateinit var recognizer: SpeechRecognizer

    private val gold = Color.rgb(255, 190, 50)
    private val black = Color.rgb(15, 15, 20)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tts = TextToSpeech(this, this)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(24, 30, 24, 20)
        layout.setBackgroundColor(black)

        val title = TextView(this)
        title.text = "ARSAM X"
        title.textSize = 32f
        title.setTextColor(gold)
        title.gravity = Gravity.CENTER
        layout.addView(title)

        val subtitle = TextView(this)
        subtitle.text = "دستیار هوشمند شخصی"
        subtitle.textSize = 17f
        subtitle.setTextColor(Color.WHITE)
        subtitle.gravity = Gravity.CENTER
        layout.addView(subtitle)

        output = TextView(this)
        output.text = "سلام آرسام! من ARSAM X هستم."
        output.textSize = 18f
        output.setTextColor(Color.WHITE)
        output.setPadding(12, 35, 12, 35)
        output.gravity = Gravity.CENTER
        layout.addView(output)

        input = EditText(this)
        input.hint = "پیامت را بنویس..."
        input.setTextColor(Color.WHITE)
        input.setHintTextColor(Color.GRAY)
        input.setSingleLine(true)
        layout.addView(input)

        val sendButton = makeButton("ارسال پیام")
        layout.addView(sendButton)

        sendButton.setOnClickListener {
            val message = input.text.toString().trim()

            if (message.isNotEmpty()) {
                handleCommand(message)
                input.text.clear()
            }
        }

        val voiceButton = makeButton("🎤 صحبت با ARSAM X")
        layout.addView(voiceButton)

        voiceButton.setOnClickListener {
            startListening()
        }

        val speakButton = makeButton("🔊 خواندن پاسخ")
        layout.addView(speakButton)

        speakButton.setOnClickListener {
            speak(output.text.toString())
        }

        val helpButton = makeButton("راهنما")
        layout.addView(helpButton)

        helpButton.setOnClickListener {
            output.text = """
                فرمان‌های من:

                سلام
                ساعت
                زنگ ساعت ۷
                گوگل
                راهنما

                برای صحبت کردن از دکمه میکروفون استفاده کن.
            """.trimIndent()
        }

        setContentView(layout)

        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            recognizer = SpeechRecognizer.createSpeechRecognizer(this)

            recognizer.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    output.text = "دارم گوش می‌دهم..."
                }

                override fun onBeginningOfSpeech() {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {}

                override fun onError(error: Int) {
                    output.text = "تشخیص صدا انجام نشد. دوباره تلاش کن."
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(
                        SpeechRecognizer.RESULTS_RECOGNITION
                    )

                    val message = matches?.firstOrNull()

                    if (message != null) {
                        handleCommand(message)
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {}

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }
    }

    private fun makeButton(text: String): Button {
        val button = Button(this)
        button.text = text
        button.setTextColor(Color.BLACK)
        button.setBackgroundColor(gold)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.setMargins(0, 10, 0, 10)
        button.layoutParams = params

        return button
    }

    private fun startListening() {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            output.text = "سرویس تشخیص صدا در گوشی در دسترس نیست."
            return
        }

        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.RECORD_AUDIO),
                100
            )
            return
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa-IR")
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "صحبت کن")

        recognizer.startListening(intent)
    }

    private fun handleCommand(message: String) {
        val text = message.lowercase(Locale.ROOT)

        val answer = when {
            text.contains("سلام") ->
                "سلام آرسام! چطور می‌توانم کمکت کنم؟"

            text.contains("ساعت") && text.contains("زنگ") -> {
                setAlarm()
                "درخواست تنظیم زنگ ساعت را باز کردم."
            }

            text.contains("گوگل") ->
                "در حال باز کردن گوگل."

            text.contains("راهنما") ->
                "می‌توانی با من صحبت کنی یا پیام بنویسی."

            text.contains("اسمت") ->
                "اسم من ARSAM X است."

            text.contains("خوبی") ->
                "ممنون! آماده انجام فرمان‌ها هستم."

            else ->
                "پیامت را دریافت کردم. هنوز پاسخ هوشمند آنلاین به من متصل نشده است."
        }

        output.text = answer

        if (text.contains("گوگل")) {
            openGoogle()
        }

        speak(answer)
    }

    private fun openGoogle() {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                android.net.Uri.parse("https://www.google.com")
            )
            startActivity(intent)
        } catch (e: Exception) {
            output.text = "باز کردن گوگل ناموفق بود."
        }
    }

    private fun setAlarm() {
        try {
            val intent = Intent(
                android.provider.AlarmClock.ACTION_SET_ALARM
            )

            intent.putExtra(
                android.provider.AlarmClock.EXTRA_HOUR,
                7
            )

            intent.putExtra(
                android.provider.AlarmClock.EXTRA_MINUTES,
                0
            )

            intent.putExtra(
                android.provider.AlarmClock.EXTRA_MESSAGE,
                "ARSAM X"
            )

            startActivity(intent)

        } catch (e: Exception) {
            output.text = "امکان باز کردن تنظیم زنگ وجود ندارد."
        }
    }

    private fun speak(text: String) {
        if (::tts.isInitialized) {
            tts.speak(
                text,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "ARSAM_X"
            )
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts.language = Locale("fa", "IR")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )

        if (requestCode == 100 &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startListening()
        } else {
            output.text = "برای استفاده از میکروفون باید مجوز بدهی."
        }
    }

    override fun onDestroy() {
        if (::recognizer.isInitialized) {
            recognizer.destroy()
        }

        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }

        super.onDestroy()
    }
}
