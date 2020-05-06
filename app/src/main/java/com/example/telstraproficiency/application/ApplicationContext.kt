package com.example.telstraproficiency.application

import android.app.Application
import android.content.Context

class ApplicationContext : Application() {
    companion object {
        lateinit var context: Context
    }

    /**
     * This method is used to initialize the context
     */
    override fun onCreate() {
        super.onCreate()
        context = this
    }
}