package com.thinh.flashcardlearning.flashcard._di

import com.thinh.flashcardlearning.flashcard.addingflashcard.impl.AddingFlashCardViewModel
import com.thinh.flashcardlearning.flashcard.datasource.local.LocalDataSource
import com.thinh.flashcardlearning.flashcard.datasource.local.impl.LocalDataSourceImpl
import com.thinh.flashcardlearning.flashcard.flashcardlist.impl.FlashCardListViewModel
import com.thinh.flashcardlearning.flashcard.repository.FlashCardRepository
import com.thinh.flashcardlearning.flashcard.repository.impl.FlashCardRepositoryImpl
import com.thinh.flashcardlearning.flashcard.usecase.AddFlashCardUseCase
import com.thinh.flashcardlearning.flashcard.usecase.GetFlashCardsUseCase
import com.thinh.flashcardlearning.flashcard.usecase.UpdateDoneFlashCardUseCase
import com.thinh.flashcardlearning.flashcard.usecase.im.UpdateDoneFlashCardUseCaseImpl
import com.thinh.flashcardlearning.flashcard.usecase.impl.AddFlashCardUseCaseImpl
import com.thinh.flashcardlearning.flashcard.usecase.impl.GetFlashCardsUseCaseImpl
import org.koin.dsl.module

val flashCardModule = module {
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single<FlashCardRepository> { FlashCardRepositoryImpl(get()) }
    single<GetFlashCardsUseCase> { GetFlashCardsUseCaseImpl(get()) }
    single<AddFlashCardUseCase> { AddFlashCardUseCaseImpl(get()) }
    single<UpdateDoneFlashCardUseCase> { UpdateDoneFlashCardUseCaseImpl(get()) }

    single<FlashCardListViewModel> { FlashCardListViewModel(get(), get()) }
    single<AddingFlashCardViewModel> { AddingFlashCardViewModel(get()) }
}