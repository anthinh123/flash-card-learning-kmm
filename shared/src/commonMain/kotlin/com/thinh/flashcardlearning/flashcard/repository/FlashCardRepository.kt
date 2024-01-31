package com.thinh.flashcardlearning.flashcard.repository

import kotlinx.coroutines.flow.Flow

interface FlashCardRepository {
    suspend fun getFlashCards(): Flow<List<FlashCardDo>>
    suspend fun addFlashCard(flashCardDo: FlashCardDo): Boolean
}