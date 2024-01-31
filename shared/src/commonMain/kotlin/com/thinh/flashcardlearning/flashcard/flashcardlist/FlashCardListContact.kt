package com.thinh.flashcardlearning.flashcard.flashcardlist

import com.thinh.flashcardlearning.flashcard.domain.FlashCardPo
import kotlinx.coroutines.flow.StateFlow

interface FlashCardListContact {
    val flashCardListState: StateFlow<FlashCardListState>

    fun event(event: Event)
}

data class FlashCardListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val flashCards: List<FlashCardPo> = listOf()
)

sealed class Event {
    class OnDoneIconSelected(val id: Long) : Event()
    class OnSpeakIconSelected(val id: Long) : Event()
    class OnTranslateIconSelected(val id: Long) : Event()
}

