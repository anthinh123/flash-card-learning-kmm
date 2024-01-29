package com.thinh.flashcardlearning.flashcard.flashcardlist.impl

import com.thinh.common.BaseViewModel
import com.thinh.flashcardlearning.flashcard.flashcardlist.FlashCardListContact
import com.thinh.flashcardlearning.flashcard.flashcardlist.FlashCardListState
import com.thinh.flashcardlearning.flashcard.usecase.GetFlashCardsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FlashCardListViewModel(
    private val getFlashCardsUseCase: GetFlashCardsUseCase
) : BaseViewModel(), FlashCardListContact {

    private var _flashCardListState: MutableStateFlow<FlashCardListState> =
        MutableStateFlow(FlashCardListState(isLoading = true))
    override val flashCardListState: StateFlow<FlashCardListState> = _flashCardListState

    init {
        loadFlashCards()
    }

    private fun loadFlashCards() {
        scope.launch {
            getFlashCardsUseCase.execute(Unit).collect {
                _flashCardListState.emit(FlashCardListState(flashCards = it))
            }
        }
    }

}