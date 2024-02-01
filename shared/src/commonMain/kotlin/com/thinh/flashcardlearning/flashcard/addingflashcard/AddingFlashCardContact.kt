package com.thinh.flashcardlearning.flashcard.addingflashcard

import com.thinh.common.BaseMviContract
import com.thinh.flashcardlearning.flashcard.addingflashcard.AddingFlashCardContact.AddingFlashCardUiState
import com.thinh.flashcardlearning.flashcard.addingflashcard.AddingFlashCardContact.Event
import com.thinh.flashcardlearning.flashcard.domain.FlashCardPo

interface AddingFlashCardContact : BaseMviContract<AddingFlashCardUiState, Event> {

    data class AddingFlashCardUiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val flashCardPo: FlashCardPo = FlashCardPo(id = -1, original = ""),
        val isSavingSuccess: Boolean = false
    )

    sealed class Event {
        object OnSave : Event()

        class SetOriginalContent(val original: String) : Event()

        class SetMeaningContent(val meaning: String) : Event()
    }
}

