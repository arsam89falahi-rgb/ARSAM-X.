package com.arsam.x

object LanguageManager {

    fun detectLanguage(text: String): String {
        val input = text.trim()

        if (input.isEmpty()) return "unknown"

        val persianChars = Regex("[پچژگکی]")
        val arabicChars = Regex("[ضصثقفغعهخحج]")
        val latinChars = Regex("[a-zA-Z]")

        return when {
            persianChars.containsMatchIn(input) -> "fa"
            arabicChars.containsMatchIn(input) -> "ar"
            latinChars.containsMatchIn(input) -> detectLatinLanguage(input)
            else -> "unknown"
        }
    }

    private fun detectLatinLanguage(text: String): String {
        val words = text.lowercase()
            .replace(Regex("[^a-zäöüß ]"), "")
            .split(Regex("\\s+"))
            .toSet()

        val germanWords = setOf(
            "ich", "du", "und", "ist", "nicht",
            "das", "der", "die", "hallo", "danke",
            "bitte", "guten", "morgen"
        )

        val englishWords = setOf(
            "the", "is", "are", "you", "hello",
            "thanks", "please", "what", "how",
            "good", "morning", "and"
        )

        val germanScore = words.intersect(germanWords).size
        val englishScore = words.intersect(englishWords).size

        return when {
            germanScore > englishScore -> "de"
            englishScore > germanScore -> "en"
            else -> "unknown"
        }
    }

    fun languageName(code: String): String {
        return when (code) {
            "fa" -> "فارسی"
            "en" -> "English"
            "de" -> "Deutsch"
            "ar" -> "العربية"
            "ku" -> "کوردی"
            else -> "نامشخص"
        }
    }

    fun responseLanguageInstruction(code: String): String {
        return when (code) {
            "fa" -> "به زبان فارسی پاسخ بده."
            "en" -> "Respond in English."
            "de" -> "Antworte auf Deutsch."
            "ar" -> "أجب باللغة العربية."
            "ku" -> "بە زمانی کوردی وەڵام بدە."
            else -> "زبان پیام کاربر را تشخیص بده و به همان زبان پاسخ بده."
        }
    }
}
