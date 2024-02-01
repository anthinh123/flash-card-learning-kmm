package com.thinh.flashcardlearning.android.screens

import androidx.navigation.NavHostController

enum class AppScreen(val route: String) {
    FLASH_CARD_LIST_SCREEN("flash_card_list_screen"),
    ADDING_FLASH_CARD_SCREEN("adding_flash_card_screen")
}

class AppActions(private val navController: NavHostController) {
    fun navigateToAddingFlashCardScreen() {
        navController.navigate(AppScreen.ADDING_FLASH_CARD_SCREEN.route)
    }

}