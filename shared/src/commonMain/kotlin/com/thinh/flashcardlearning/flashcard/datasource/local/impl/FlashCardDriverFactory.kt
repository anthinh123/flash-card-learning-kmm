package com.thinh.flashcardlearning.flashcard.datasource.local.impl

import app.cash.sqldelight.db.SqlDriver

expect class FlashCardDriverFactory {
    fun createSqlDriver(): SqlDriver
}