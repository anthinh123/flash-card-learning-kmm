package com.thinh.flashcardlearning.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thinh.flashcardlearning.android.screens.AddingFlashCardScreen
import com.thinh.flashcardlearning.android.screens.AppActions
import com.thinh.flashcardlearning.android.screens.FlashCardListScreen
import com.thinh.flashcardlearning.android.screens.AppScreen

@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val navActions: AppActions = remember(navController) {
        AppActions(navController)
    }

    Scaffold {
        AppNavHost(
            navController = navController,
            navActions = navActions,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    navActions: AppActions,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.FLASH_CARD_LIST_SCREEN.route,
        modifier = modifier,
    ) {
        composable(AppScreen.FLASH_CARD_LIST_SCREEN.route) {
            FlashCardListScreen(navigateToAddingFlashCardScreen = { navActions.navigateToAddingFlashCardScreen() })
        }

        composable(AppScreen.ADDING_FLASH_CARD_SCREEN.route) {
            AddingFlashCardScreen(
                onUpClicked = { navController.navigateUp() }
            )
        }
    }

}