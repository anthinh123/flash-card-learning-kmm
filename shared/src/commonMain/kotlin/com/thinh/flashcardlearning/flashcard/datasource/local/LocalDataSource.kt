package com.thinh.flashcardlearning.flashcard.datasource.local

import com.thinh.flashcardlearning.flashcard.datasource.model.FlashCardDo
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getFlashCards(): Flow<List<FlashCardDo>>
    fun addFlashCard(flashCardDo: FlashCardDo): Boolean
    fun updateDoneFlashCard(id: Long, isDone: Boolean): Boolean
}