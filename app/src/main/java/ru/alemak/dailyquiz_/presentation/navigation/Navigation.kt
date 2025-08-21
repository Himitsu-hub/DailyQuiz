package ru.alemak.dailyquiz_.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.alemak.dailyquiz_.presentation.history.HistoryScreen
import ru.alemak.dailyquiz_.presentation.history.viewmodel.HistoryViewModel
import ru.alemak.dailyquiz_.presentation.quiz.QuizScreen
import ru.alemak.dailyquiz_.presentation.quiz.viewmodel.QuizViewModel
import ru.alemak.dailyquiz_.presentation.review.ReviewScreen
import ru.alemak.dailyquiz_.presentation.review.viewmodel.ReviewViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "quiz"
    ) {
        composable("quiz") {
            val viewModel: QuizViewModel = hiltViewModel()
            QuizScreen(
                viewModel = viewModel,
                onNavigateToHistory = { navController.navigate("history") },
                onNavigateToReview = { quizId -> navController.navigate("review/$quizId") }
            )
        }

        composable("history") {
            val viewModel: HistoryViewModel = hiltViewModel()
            HistoryScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToReview = { quizId -> navController.navigate("review/$quizId") }
            )
        }

        composable("review/{quizId}") { backStackEntry ->
            val viewModel: ReviewViewModel = hiltViewModel()
            ReviewScreen(
                viewModel = viewModel,
                quizId = backStackEntry.arguments?.getString("quizId")?.toLongOrNull() ?: 0L,
                onBackToInitial = {
                    navController.navigate("quiz") {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}