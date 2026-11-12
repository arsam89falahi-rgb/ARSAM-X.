package com.arsam.x

import android.content.Context
import java.io.File

object CodingService {

    fun createFile(
        context: Context,
        fileName: String,
        content: String
    ): Boolean {
        return try {
            if (!fileName.matches(Regex("[a-zA-Z0-9_.-]+"))) {
                return false
            }

            val directory = File(context.filesDir, "projects")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val file = File(directory, fileName)
            file.writeText(content)

            true
        } catch (e: Exception) {
            false
        }
    }

    fun readFile(
        context: Context,
        fileName: String
    ): String? {
        return try {
            if (!fileName.matches(Regex("[a-zA-Z0-9_.-]+"))) {
                return null
            }

            val file = File(context.filesDir, "projects/$fileName")

            if (file.exists() && file.isFile) {
                file.readText()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    fun deleteFile(
        context: Context,
        fileName: String
    ): Boolean {
        return try {
            if (!fileName.matches(Regex("[a-zA-Z0-9_.-]+"))) {
                return false
            }

            val file = File(context.filesDir, "projects/$fileName")
            file.exists() && file.isFile && file.delete()
        } catch (e: Exception) {
            false
        }
    }
}
