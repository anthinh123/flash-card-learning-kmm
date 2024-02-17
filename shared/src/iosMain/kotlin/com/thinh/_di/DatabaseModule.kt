package com.thinh._di

import app.cash.sqldelight.db.SqlDriver
import com.thinh.flashcardlearning.db.FlashCardLearningDatabase
import com.thinh.flashcardlearning.flashcard.datasource.local.impl.FlashCardDriverFactory
import org.koin.dsl.module

val databaseModule = module {
    single<SqlDriver> { FlashCardDriverFactory().createSqlDriver() }
    single<FlashCardLearningDatabase> {FlashCardLearningDatabase(get())}
}