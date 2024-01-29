package com.thinh.flashcardlearning.flashcard.datasource.local.impl

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.thinh.flashcardlearning.db.FlashCardLearningDatabase

actual class FlashCardDriverFactory  {

    actual fun createSqlDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = FlashCardLearningDatabase.Schema,
            name = "FlashCard.Database.db"
        )
    }
}