package com.thinh.flashcardlearning.android

import android.app.Application
import com.thinh.di.sharedKoinModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FlashCardApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = sharedKoinModule
        startKoin {
            androidContext(this@FlashCardApplication)
            modules(modules)
        }
    }
}