package com.thinh.flashcardlearning.flashcard.domain

data class FlashCardPo(
    val id: Long,
    val original: String,
    val meaning: String?,
    val urlImage: String?,
    val urlVoice: String?,
    val done: Boolean?
)