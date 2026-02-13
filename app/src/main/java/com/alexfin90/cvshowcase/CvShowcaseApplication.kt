package com.alexfin90.cvshowcase

import android.app.Application
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy.Builder
import android.os.StrictMode.VmPolicy

class CvApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        setStrictModePolicy()
    }
}


/**
 * Set a thread policy that detects all potential problems on the main thread, such as network
 * and disk access. Also detects potential memory leaks in the application
 *
 * If a problem is found, the offending call will be logged and the application will be killed.
 * @see <a href="https://medium.com/wizeline-mobile/raising-the-bar-with-android-strictmode-7042d8a9e67b">Android StrictMode best practices</a>
 */
private fun setStrictModePolicy() {
    if (BuildConfig.DEBUG) {
        StrictMode.setThreadPolicy(
            Builder().detectAll().penaltyLog().penaltyFlashScreen().build(),
        )
        StrictMode.setVmPolicy(
            VmPolicy.Builder().detectAll().penaltyLog().build()
        )
    }
}