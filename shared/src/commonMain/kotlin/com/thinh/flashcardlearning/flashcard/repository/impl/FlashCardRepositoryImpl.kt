package com.thinh.flashcardlearning.flashcard.repository.impl

import com.thinh.flashcardlearning.flashcard.datasource.local.LocalDataSource
import com.thinh.flashcardlearning.flashcard.repository.FlashCardDo
import com.thinh.flashcardlearning.flashcard.repository.FlashCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FlashCardRepositoryImpl(
    private val localDataSource: LocalDataSource
) : FlashCardRepository {

    override suspend fun getFlashCards(): Flow<List<FlashCardDo>> = flow {
        val flashCards = localDataSource.getFlashCards()
        val flashCardDos = flashCards.map {
            FlashCardDo(
                id = it.id,
                original = it.original,
                meaning = it.meaning,
                urlImage = it.urlImage,
                urlVoice = it.urlVoice,
                done = it.done,
            )
        }
        emit(flashCardDos)
    }

    override suspend fun addFlashCard(flashCardDo: FlashCardDo): Boolean {
        return localDataSource.addFlashCard(flashCardDo)
    }

}