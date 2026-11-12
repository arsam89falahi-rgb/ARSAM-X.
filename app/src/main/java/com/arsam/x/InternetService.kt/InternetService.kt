package com.arsam.x

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.provider.Settings

class InternetService(private val context: Context) {

    fun isInternetConnected(): Boolean {
        return try {
            val manager = context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

            val network = manager.activeNetwork ?: return false

            val capabilities =
                manager.getNetworkCapabilities(network) ?: return false

            capabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_INTERNET
            ) && capabilities.hasCapability(
                NetworkCapabilities.NET_CAPABILITY_VALIDATED
            )
        } catch (e: Exception) {
            false
        }
    }

    fun openInternetSettings() {
        val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    fun openWifiSettings() {
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    fun openVpnSettings() {
        val intent = Intent(Settings.ACTION_VPN_SETTINGS).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    fun internetStatus(): String {
        return if (isInternetConnected()) {
            "اتصال اینترنت برقرار است."
        } else {
            "اتصال اینترنت برقرار نیست."
        }
    }
}
