package com.thinh._di

import com.thinh.di.sharedKoinModule
import com.thinh.flashcardlearning.flashcard.addingflashcard.impl.AddingFlashCardViewModel
import com.thinh.flashcardlearning.flashcard.flashcardlist.impl.FlashCardListViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

fun initKoin() {
    val modules = sharedKoinModule + databaseModule

    startKoin {
        modules(modules)
    }
}

class FlashCardLearningInjector : KoinComponent {
    val flashCardListViewModel: FlashCardListViewModel by inject()
    val addingFlashCardViewModel: AddingFlashCardViewModel by inject()
}