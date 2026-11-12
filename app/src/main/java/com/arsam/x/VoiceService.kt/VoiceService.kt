package com.arsam.x

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import java.util.Locale

class VoiceService(private val activity: Activity) {

    private var textToSpeech: TextToSpeech? = null
    private var isReady = false

    init {
        textToSpeech = TextToSpeech(activity) { status ->
            isReady = status == TextToSpeech.SUCCESS

            if (isReady) {
                textToSpeech?.language = Locale.getDefault()
            }
        }
    }

    fun speak(text: String, languageCode: String = "fa") {
        if (!isReady || text.isBlank()) return

        val locale = when (languageCode) {
            "fa" -> Locale("fa", "IR")
            "en" -> Locale.US
            "de" -> Locale.GERMANY
            "ar" -> Locale("ar")
            "ku" -> Locale("ku")
            else -> Locale.getDefault()
        }

        val result = textToSpeech?.setLanguage(locale)

        if (result != TextToSpeech.LANG_MISSING_DATA &&
            result != TextToSpeech.LANG_NOT_SUPPORTED
        ) {
            textToSpeech?.speak(
                text,
                TextToSpeech.QUEUE_FLUSH,
                null,
                "ARSAM_X_SPEECH"
            )
        }
    }

    fun startListening(requestCode: Int = 1001) {
        try {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(
                    RecognizerIntent.EXTRA_PROMPT,
                    "با ARSAM X صحبت کن"
                )
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
            }

            activity.startActivityForResult(intent, requestCode)

        } catch (e: Exception) {
            speak("سرویس تشخیص گفتار در دسترس نیست.")
        }
    }

    fun destroy() {
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
        isReady = false
    }
}
