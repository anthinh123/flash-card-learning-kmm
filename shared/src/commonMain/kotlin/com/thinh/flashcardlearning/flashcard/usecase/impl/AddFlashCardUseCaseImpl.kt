package com.thinh.flashcardlearning.flashcard.usecase.impl

import com.thinh.flashcardlearning.flashcard.domain.FlashCardPo
import com.thinh.flashcardlearning.flashcard.domain.toFlashCardDo
import com.thinh.flashcardlearning.flashcard.repository.FlashCardRepository
import com.thinh.flashcardlearning.flashcard.usecase.AddFlashCardUseCase

class AddFlashCardUseCaseImpl(
    private val repository: FlashCardRepository
) : AddFlashCardUseCase {

    override suspend fun execute(input: FlashCardPo): Boolean {
        return repository.addFlashCard(input.toFlashCardDo())
    }

}