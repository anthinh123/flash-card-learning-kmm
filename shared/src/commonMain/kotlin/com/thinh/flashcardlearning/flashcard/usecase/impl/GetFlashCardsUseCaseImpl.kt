package com.thinh.flashcardlearning.flashcard.usecase.impl

import com.thinh.flashcardlearning.flashcard.domain.FlashCardPo
import com.thinh.flashcardlearning.flashcard.repository.FlashCardDo
import com.thinh.flashcardlearning.flashcard.repository.FlashCardRepository
import com.thinh.flashcardlearning.flashcard.usecase.GetFlashCardsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFlashCardsUseCaseImpl(
    private val flashCardRepository: FlashCardRepository
) : GetFlashCardsUseCase {

    override suspend fun execute(input: Unit): Flow<List<FlashCardPo>> =
        flashCardRepository.getFlashCards().map { mapToFlashCardPos(it) }

    private fun mapToFlashCardPos(flashCardDos: List<FlashCardDo>) = flashCardDos.map {
        FlashCardPo(
            id = it.id,
            original = it.original,
            meaning = it.meaning,
            urlImage = it.urlImage,
            urlVoice = it.urlVoice,
            done = it.done
        )
    }
}