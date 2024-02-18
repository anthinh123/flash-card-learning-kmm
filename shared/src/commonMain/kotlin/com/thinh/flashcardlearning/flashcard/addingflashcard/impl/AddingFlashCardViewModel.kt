package com.thinh.flashcardlearning.flashcard.addingflashcard.impl

import com.thinh.common.BaseViewModel
import com.thinh.flashcardlearning.flashcard.addingflashcard.AddingFlashCardContact
import com.thinh.flashcardlearning.flashcard.addingflashcard.AddingFlashCardContact.AddingFlashCardUiState
import com.thinh.flashcardlearning.flashcard.addingflashcard.AddingFlashCardContact.Event
import com.thinh.flashcardlearning.flashcard.usecase.AddFlashCardUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddingFlashCardViewModel(
    private val addFlashCardUseCase: AddFlashCardUseCase
) : BaseViewModel(), AddingFlashCardContact {

    private val _uiState = MutableStateFlow(AddingFlashCardUiState())
    override val uiState: StateFlow<AddingFlashCardUiState> = _uiState

    override fun event(event: Event) {
        when (event) {
            Event.OnSave -> addNewFlashCard()
            is Event.SetMeaningContent -> setMeaningText(event.meaning)
            is Event.SetOriginalContent -> setOriginalText(event.original)
        }
    }

    private fun addNewFlashCard() {
        scope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val original = _uiState.value.flashCardPo.original
            val meaning = _uiState.value.flashCardPo.meaning
            if (original.isEmpty() || meaning.isEmpty()) {
                _uiState.update {
                    AddingFlashCardUiState(
                        error = "Need has contents for sentence or meaning"
                    )
                }
                return@launch
            }

            addFlashCardUseCase.execute(_uiState.value.flashCardPo)

            _uiState.update {
                AddingFlashCardUiState(
                    isSavingSuccess = true
                )
            }
        }
    }

    private fun setOriginalText(originalText: String) {
        scope.launch {
            val flashCardPo = _uiState.value.flashCardPo.copy(original = originalText)
            _uiState.value = _uiState.value.copy(flashCardPo = flashCardPo)
        }
    }

    private fun setMeaningText(meaning: String) {
        scope.launch {
            val flashCardPo = _uiState.value.flashCardPo.copy(meaning = meaning)
            _uiState.value = _uiState.value.copy(flashCardPo = flashCardPo)
        }
    }

}