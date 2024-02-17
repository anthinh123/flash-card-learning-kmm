package com.thinh.flashcardlearning.flashcard.repository.impl

import com.thinh.flashcardlearning.flashcard.datasource.local.LocalDataSource
import com.thinh.flashcardlearning.flashcard.datasource.model.FlashCardDo
import com.thinh.flashcardlearning.flashcard.repository.FlashCardRepository
import kotlinx.coroutines.flow.Flow

class FlashCardRepositoryImpl(
    private val localDataSource: LocalDataSource
) : FlashCardRepository {

    override suspend fun getFlashCards(): Flow<List<FlashCardDo>> {
        return localDataSource.getFlashCards()
    }

    override suspend fun addFlashCard(flashCardDo: FlashCardDo): Boolean {
        return localDataSource.addFlashCard(flashCardDo)
    }

    override suspend fun updateDoneFlashCard(id: Long, isDone: Boolean) =
        localDataSource.updateDoneFlashCard(id = id, isDone = isDone)

}