package com.thinh.flashcardlearning.flashcard.flashcardlist.impl

import com.thinh.common.BaseViewModel
import com.thinh.flashcardlearning.flashcard.domain.FlashCardPo
import com.thinh.flashcardlearning.flashcard.flashcardlist.Event
import com.thinh.flashcardlearning.flashcard.flashcardlist.FlashCardListContact
import com.thinh.flashcardlearning.flashcard.flashcardlist.FlashCardListState
import com.thinh.flashcardlearning.flashcard.usecase.AddFlashCardUseCase
import com.thinh.flashcardlearning.flashcard.usecase.GetFlashCardsUseCase
import com.thinh.flashcardlearning.flashcard.usecase.UpdateDoneFlashCardUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlashCardListViewModel(
    private val getFlashCardsUseCase: GetFlashCardsUseCase,
    private val updateDoneFlashCardUseCase: UpdateDoneFlashCardUseCase
) : BaseViewModel(), FlashCardListContact {

    private val _flashCardListState: MutableStateFlow<FlashCardListState> =
        MutableStateFlow(FlashCardListState(isLoading = true))
    override val flashCardListState: StateFlow<FlashCardListState> = _flashCardListState

    init {
        println("thinhav FlashCardListViewModel init")
        loadFlashCards()
    }

    private fun loadFlashCards() {
        scope.launch {
            getFlashCardsUseCase.execute(Unit).collect {
                _flashCardListState.emit(FlashCardListState(flashCards = it))
            }
        }
    }

    override fun event(event: Event) {
        when (event) {
            is Event.OnDoneIconSelected -> {
                updatePhraseIsDone(id = event.id, isDone = true)
            }

            is Event.OnTranslateIconSelected -> {
                handleTranslateIconSelected(event.id)
            }

            is Event.OnSpeakIconSelected -> println(
                "thinhav OnSpeakIconSelected"
            )
        }
    }

    private fun handleTranslateIconSelected(id: Long) {
        scope.launch {
            _flashCardListState.update {
                it.copy(isLoading = true)
            }
            val list = _flashCardListState.value.flashCards.map {
                if (id == it.id) {
                    it.copy(isDisplayMeaning = !it.isDisplayMeaning)
                } else {
                    it.copy(isDisplayMeaning = false)
                }
            }
            _flashCardListState.update {
                FlashCardListState(flashCards = list)
            }
        }
    }

    private fun updatePhraseIsDone(id: Long, isDone: Boolean) {
        scope.launch {
            updateDoneFlashCardUseCase.execute(
                UpdateDoneFlashCardUseCase.Input(
                    id = id,
                    isDone = isDone
                )
            )
        }
    }

    private fun mockData(): List<FlashCardPo> = listOf(
        FlashCardPo(
            id = 1,
            original = "It looks like everyone is here, so let's get started.",
            meaning = "Có vẻ như mọi người đều ở đây, vì vậy chúng ta hãy bắt đầu",
        ),
        FlashCardPo(
            id = 2,
            original = "We're about to start the meeting, so everyone please take your seats.",
            meaning = "Chúng ta sắp bắt đầu cuộc họp, vì vậy mọi người hãy ổn định chỗ ngồi của mình.",
        ),
        FlashCardPo(
            id = 3,
            original = "I'd love to hear your opinion on this matter ",
            meaning = "Tôi rất muốn nghe ý kiến của bạn về vấn đề này",
        ),
        FlashCardPo(
            id = 4,
            original = " I heard you quit your job as a designer a few months ago ",
            meaning = "Tớ nghe nói cậu vừa bỏ công việc thiết kế một vài tháng trước",
        ),
        FlashCardPo(
            id = 5,
            original = "Fortunately, I have been employed as a software engineer for a food company in our city and I have been working here for nearly 3 weeks ",
            meaning = "May mắn là tớ đã được tuyển dụng làm kỹ sư phần mềm cho một công ty thực phẩm của thành phố chúng ta và tớ đã làm việc ở đây được gần 3 tuần rồi.",
        ),
        FlashCardPo(
            id = 6,
            original = "that’s pretty cool. Is it much different compared to your previous job?",
            meaning = "thật tốt quá. Nó có khác gì nhiều so với công việc trước không?",
        )
    )
}