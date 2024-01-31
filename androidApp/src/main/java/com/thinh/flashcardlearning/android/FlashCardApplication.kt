package com.thinh.flashcardlearning.android

import android.app.Application
import com.thinh.di.sharedKoinModule
import com.thinh.flashcardlearning.android._di.flashCardDatabaseModule
import com.thinh.flashcardlearning.android._di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class FlashCardApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = sharedKoinModule + viewModelModule + flashCardDatabaseModule
        startKoin {
            androidContext(this@FlashCardApplication)
            modules(modules)
        }
    }
}