package com.thinh.flashcardlearning.flashcard.domain

import com.thinh.flashcardlearning.flashcard.repository.FlashCardDo

data class FlashCardPo(
    val id: Long,
    val original: String,
    val meaning: String = "",
    val urlImage: String = "",
    val urlVoice: String = "",
    val done: Boolean = false,
    val isDisplayMeaning: Boolean = false,
)

fun FlashCardPo.toFlashCardDo(): FlashCardDo {
    return FlashCardDo(
        id = this.id,
        original = this.original,
        meaning = this.meaning,
        urlImage = this.urlImage,
        urlVoice = this.urlVoice,
        done = this.done
    )
}