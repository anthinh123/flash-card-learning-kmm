package com.thinh.flashcardlearning.flashcard.datasource.local

import com.thinh.flashcardlearning.flashcard.repository.FlashCardDo

interface LocalDataSource {
    fun getFlashCards(): List<FlashCardDo>
}