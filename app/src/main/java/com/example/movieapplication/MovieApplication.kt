package com.example.movieapplication

import android.app.Application
import timber.log.Timber

/**
 * Subclass of Application used for setting up global dependencies
 */
class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Timber logging setup
        Timber.plant(Timber.DebugTree())
    }
}