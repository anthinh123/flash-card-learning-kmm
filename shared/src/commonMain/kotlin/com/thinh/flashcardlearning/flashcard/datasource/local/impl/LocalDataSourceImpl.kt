package com.thinh.flashcardlearning.flashcard.datasource.local.impl

import com.thinh.flashcardlearning.db.FlashCardLearningDatabase
import com.thinh.flashcardlearning.flashcard.datasource.local.LocalDataSource
import com.thinh.flashcardlearning.flashcard.repository.FlashCardDo

class LocalDataSourceImpl(private val database: FlashCardLearningDatabase) : LocalDataSource {

    override fun getFlashCards(): List<FlashCardDo> {
        return database.flashCardLearningDatabaseQueries.getAllFlashCard(::mapToFlashCardDo)
            .executeAsList()
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