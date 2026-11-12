package com.arsam.x

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PhoneService(private val activity: Activity) {

    fun callContactNumber(phoneNumber: String): Boolean {
        if (phoneNumber.isBlank()) return false

        return try {
            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:${Uri.encode(phoneNumber)}")
            )
            activity.startActivity(intent)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun hasContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestContactsPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.READ_CONTACTS),
            2001
        )
    }
}
