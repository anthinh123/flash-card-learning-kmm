package com.thinh.flashcardlearning.android._di

import com.thinh.flashcardlearning.flashcard.addingflashcard.impl.AddingFlashCardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import com.thinh.flashcardlearning.flashcard.flashcardlist.impl.FlashCardListViewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FlashCardListViewModel(get(), get()) }
    viewModel { AddingFlashCardViewModel(get()) }
}