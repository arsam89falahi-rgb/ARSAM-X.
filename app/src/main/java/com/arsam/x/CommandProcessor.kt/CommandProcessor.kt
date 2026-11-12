package com.arsam.x

enum class CommandType {
    OPEN_WEBSITE,
    OPEN_APP,
    CREATE_REMINDER,
    UNKNOWN
}

data class ParsedCommand(
    val type: CommandType,
    val value: String
)

object CommandProcessor {

    fun process(text: String): ParsedCommand {
        val command = text.trim()

        if (command.isEmpty()) {
            return ParsedCommand(CommandType.UNKNOWN, "")
        }

        val lower = command.lowercase()

        val websitePrefixes = listOf(
            "برو به سایت ",
            "باز کن سایت ",
            "open website ",
            "open site "
        )

        for (prefix in websitePrefixes) {
            if (lower.startsWith(prefix)) {
                val address = command.substring(prefix.length).trim()
                return ParsedCommand(
                    CommandType.OPEN_WEBSITE,
                    address
                )
            }
        }

        val appPrefixes = listOf(
            "برنامه ",
            "اپلیکیشن ",
            "open app "
        )

        for (prefix in appPrefixes) {
            if (lower.startsWith(prefix)) {
                val appName = command.substring(prefix.length).trim()
                return ParsedCommand(
                    CommandType.OPEN_APP,
                    appName
                )
            }
        }

        if (
            lower.startsWith("یادآوری کن ") ||
            lower.startsWith("remind me ")
        ) {
            return ParsedCommand(
                CommandType.CREATE_REMINDER,
                command
            )
        }

        return ParsedCommand(
            CommandType.UNKNOWN,
            command
        )
    }
}
