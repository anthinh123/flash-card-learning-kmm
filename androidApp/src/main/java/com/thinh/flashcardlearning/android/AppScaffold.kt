package com.thinh.flashcardlearning.android

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thinh.flashcardlearning.android.screens.FlashCardListScreen
import com.thinh.flashcardlearning.android.screens.Screen

@Composable
fun AppScaffold() {
    val navController = rememberNavController()

    Scaffold {
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.FLASH_CARD_LIST_SCREEN.route,
        modifier = modifier
    ) {
        composable(Screen.FLASH_CARD_LIST_SCREEN.route) {
            FlashCardListScreen(navigateToAddingFlashCardScreen = { println("navigateToAddingFlashCardScreen") })
        }
    }

}