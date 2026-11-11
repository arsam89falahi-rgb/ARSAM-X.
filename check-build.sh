#!/data/data/com.termux/files/usr/bin/bash

set -e

echo "===== ARSAM X BUILD CHECK ====="

echo "[1/4] Checking Java..."
java -version

echo "[2/4] Checking Gradle..."
gradle --version

echo "[3/4] Checking Android SDK..."

if [ -z "$ANDROID_HOME" ] || [ ! -d "$ANDROID_HOME/platforms" ]; then
    echo "ERROR: Android SDK is not installed."
    echo "APK build cannot continue."
    exit 1
fi

echo "[4/4] Building ARSAM X..."
gradle assembleDebug

echo "===== BUILD FINISHED ====="
echo "APK location:"
echo "app/build/outputs/apk/debug/app-debug.apk"
