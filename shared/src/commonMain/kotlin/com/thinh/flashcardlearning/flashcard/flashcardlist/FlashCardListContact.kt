package com.thinh.flashcardlearning.flashcard.flashcardlist

import com.thinh.flashcardlearning.flashcard.domain.FlashCardPo
import kotlinx.coroutines.flow.StateFlow

interface FlashCardListContact {
    val flashCardListState: StateFlow<FlashCardListState>
}

data class FlashCardListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val flashCards: List<FlashCardPo> = listOf()
)