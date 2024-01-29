package com.thinh.flashcardlearning.flashcard.datasource.local.impl

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.thinh.flashcardlearning.db.FlashCardLearningDatabase

actual class FlashCardDriverFactory(private val context: Context) {

    actual fun createSqlDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = FlashCardLearningDatabase.Schema,
            context = context,
            name = "FlashCard.Database.db"
        )
    }
}