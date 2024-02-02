package com.thinh.flashcardlearning.flashcard.datasource.local.impl

import app.cash.sqldelight.coroutines.asFlow
import com.thinh.flashcardlearning.db.FlashCardLearningDatabase
import com.thinh.flashcardlearning.flashcard.datasource.local.LocalDataSource
import com.thinh.flashcardlearning.flashcard.repository.FlashCardDo
import comthinhflashcardlearningdb.FlashCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalDataSourceImpl(private val database: FlashCardLearningDatabase) : LocalDataSource {

    private val flashCardQueries = database.flashCardLearningDatabaseQueries

    override fun getFlashCards(): Flow<List<FlashCardDo>> =
        flashCardQueries.getAllFlashCard().asFlow().map { query ->
            query.executeAsList().map(::mapToFlashCardDo)
        }

    override fun addFlashCard(flashCardDo: FlashCardDo): Boolean {
        flashCardQueries.insertFlashCard(
            flashCardDo.original,
            flashCardDo.meaning,
            flashCardDo.urlImage,
            flashCardDo.urlVoice,
            flashCardDo.done
        )
        return true
    }

    override fun updateDoneFlashCard(id: Long, isDone: Boolean): Boolean {
        flashCardQueries.updateDoneFlashCard(done = isDone, id = id)
        return true
    }

    private fun mapToFlashCardDo(flashCard: FlashCard) = with(flashCard) {
        FlashCardDo(
            id = id,
            original = original,
            meaning = meaning ?: "",
            urlImage = urlImage ?: "",
            urlVoice = urlVoice ?: "",
            done = done ?: false
        )
    }
}