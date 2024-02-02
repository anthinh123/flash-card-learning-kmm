package com.thinh.flashcardlearning.flashcard.usecase

import com.thinh.common.BaseUseCase

interface UpdateDoneFlashCardUseCase : BaseUseCase<UpdateDoneFlashCardUseCase.Input, Boolean> {
    data class Input(val id: Long, val isDone: Boolean)
}