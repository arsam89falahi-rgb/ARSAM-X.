package com.arsam.x

import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

object GitHubService {

    fun getRepositoryInfo(
        owner: String,
        repository: String
    ): String? {
        return try {
            if (!owner.matches(Regex("[A-Za-z0-9-]+")) ||
                !repository.matches(Regex("[A-Za-z0-9_.-]+"))
            ) {
                return null
            }

            val apiUrl =
                "https://api.github.com/repos/$owner/$repository"

            val connection =
                URL(apiUrl).openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.connectTimeout = 10000
            connection.readTimeout = 10000
            connection.setRequestProperty(
                "Accept",
                "application/vnd.github+json"
            )

            try {
                if (connection.responseCode != 200) {
                    return null
                }

                val response = connection.inputStream
                    .bufferedReader()
                    .use { it.readText() }

                val json = JSONObject(response)

                """
                Repository: ${json.optString("full_name")}
                Description: ${json.optString("description")}
                Stars: ${json.optInt("stargazers_count")}
                Forks: ${json.optInt("forks_count")}
                URL: ${json.optString("html_url")}
                """.trimIndent()
            } finally {
                connection.disconnect()
            }

        } catch (e: Exception) {
            null
        }
    }
}
