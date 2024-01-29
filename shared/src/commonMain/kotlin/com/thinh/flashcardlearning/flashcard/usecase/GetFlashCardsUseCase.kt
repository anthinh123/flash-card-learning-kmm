package com.thinh.flashcardlearning.flashcard.usecase

import com.thinh.common.BaseUseCase
import com.thinh.flashcardlearning.flashcard.domain.FlashCardPo
import kotlinx.coroutines.flow.Flow

interface GetFlashCardsUseCase : BaseUseCase<Unit, Flow<List<FlashCardPo>>> {
}