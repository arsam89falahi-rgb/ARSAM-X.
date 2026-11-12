package com.arsam.x

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class ArsamAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // دریافت رویدادهای رابط کاربری
    }

    override fun onInterrupt() {
        // توقف سرویس
    }

    fun getVisibleText(): List<String> {
        val result = mutableListOf<String>()
        val root = rootInActiveWindow ?: return result

        collectText(root, result)
        root.recycle()

        return result
    }

    private fun collectText(
        node: AccessibilityNodeInfo,
        result: MutableList<String>
    ) {
        node.text?.toString()?.let {
            if (it.isNotBlank()) result.add(it)
        }

        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            collectText(child, result)
            child.recycle()
        }
    }
}
