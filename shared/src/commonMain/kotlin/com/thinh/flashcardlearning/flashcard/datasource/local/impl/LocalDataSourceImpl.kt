package com.thinh.flashcardlearning.flashcard.datasource.local.impl

import com.thinh.flashcardlearning.db.FlashCardLearningDatabase
import com.thinh.flashcardlearning.flashcard.datasource.local.LocalDataSource
import com.thinh.flashcardlearning.flashcard.repository.FlashCardDo

class LocalDataSourceImpl(private val database: FlashCardLearningDatabase) : LocalDataSource {

    val flashCardQueries = database.flashCardLearningDatabaseQueries

    override fun getFlashCards(): List<FlashCardDo> {
        return database.flashCardLearningDatabaseQueries.getAllFlashCard(::mapToFlashCardDo)
            .executeAsList()
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

    private fun mapToFlashCardDo(
        id: Long,
        original: String,
        meaning: String?,
        urlImage: String?,
        urlVoice: String?,
        done: Boolean?,
    ) = FlashCardDo(
        id = id,
        original = original,
        meaning = meaning ?: "",
        urlImage = urlImage ?: "",
        urlVoice = urlVoice ?: "",
        done = done ?: false
    )
}