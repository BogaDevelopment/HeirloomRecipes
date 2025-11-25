package com.bogadevelopment.heirloomrecipes

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HeirloomRecipesApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this)
        }

        Log.d("DEBUG_AUTH", "✅ FirebaseApp initialized in Application")
    }
}
