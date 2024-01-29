package com.thinh.flashcardlearning.flashcard._di

import com.thinh.flashcardlearning.flashcard.datasource.local.LocalDataSource
import com.thinh.flashcardlearning.flashcard.datasource.local.impl.LocalDataSourceImpl
import com.thinh.flashcardlearning.flashcard.repository.FlashCardRepository
import com.thinh.flashcardlearning.flashcard.repository.impl.FlashCardRepositoryImpl
import com.thinh.flashcardlearning.flashcard.usecase.GetFlashCardsUseCase
import com.thinh.flashcardlearning.flashcard.usecase.impl.GetFlashCardsUseCaseImpl
import org.koin.dsl.module

val flashCardModule = module {
    single<LocalDataSource> { LocalDataSourceImpl(get()) }
    single<FlashCardRepository> { FlashCardRepositoryImpl(get()) }
    single<GetFlashCardsUseCase> { GetFlashCardsUseCaseImpl(get()) }
}