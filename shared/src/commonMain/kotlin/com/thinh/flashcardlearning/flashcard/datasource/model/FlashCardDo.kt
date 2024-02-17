package com.thinh.flashcardlearning.flashcard.datasource.model

data class FlashCardDo(
    val id: Long,
    val original: String,
    val meaning: String = "",
    val urlImage: String = "",
    val urlVoice: String = "",
    val done: Boolean = false
)