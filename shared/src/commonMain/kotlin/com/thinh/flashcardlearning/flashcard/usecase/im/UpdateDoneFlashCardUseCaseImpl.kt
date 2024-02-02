package com.thinh.flashcardlearning.flashcard.usecase.im

import com.thinh.flashcardlearning.flashcard.repository.FlashCardRepository
import com.thinh.flashcardlearning.flashcard.usecase.UpdateDoneFlashCardUseCase
import com.thinh.flashcardlearning.flashcard.usecase.UpdateDoneFlashCardUseCase.Input

class UpdateDoneFlashCardUseCaseImpl(
    private val repository: FlashCardRepository
) : UpdateDoneFlashCardUseCase {

    override suspend fun execute(input: Input) =
        repository.updateDoneFlashCard(id = input.id, isDone = input.isDone)
}