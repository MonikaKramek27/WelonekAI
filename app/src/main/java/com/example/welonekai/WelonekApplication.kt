package com.example.welonekai

import android.app.Application

class WelonekApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
