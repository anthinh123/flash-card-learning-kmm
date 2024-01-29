package com.thinh.flashcardlearning.android._di

import app.cash.sqldelight.db.SqlDriver
import com.thinh.flashcardlearning.db.FlashCardLearningDatabase
import com.thinh.flashcardlearning.flashcard.datasource.local.impl.FlashCardDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val flashCardDatabaseModule = module {
    single<SqlDriver> { FlashCardDriverFactory(androidContext()).createSqlDriver() }
    single<FlashCardLearningDatabase> { FlashCardLearningDatabase(get()) }
}