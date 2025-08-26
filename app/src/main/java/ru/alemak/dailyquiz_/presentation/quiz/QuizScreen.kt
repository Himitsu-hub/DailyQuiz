package ru.alemak.dailyquiz_.presentation.quiz

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.alemak.dailyquiz_.presentation.quiz.component.InitialState
import ru.alemak.dailyquiz_.presentation.quiz.component.LoadingState
import ru.alemak.dailyquiz_.presentation.quiz.component.QuizQuestionState
import ru.alemak.dailyquiz_.presentation.quiz.viewmodel.QuizViewModel

@Composable
fun QuizScreen(
    viewModel: QuizViewModel = hiltViewModel(),
    onNavigateToHistory: () -> Unit,
    onNavigateToReview: (quizId: Long) -> Unit
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val navigateToId by viewModel.navigateToReview.collectAsState()
    val answeredQuestions by viewModel.answeredQuestions.collectAsState()
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val selectedAnswer by viewModel.selectedAnswer.collectAsState()
    val score by viewModel.score.collectAsState()

    LaunchedEffect(score) {
        println("DEBUG: Current score = $score")
    }

    LaunchedEffect(navigateToId) {
        navigateToId?.let { quizId ->
            println("DEBUG: Navigating to review with score: $score")
            onNavigateToReview(quizId)
            viewModel.onNavigationComplete()
        }
    }
    when {
        isLoading -> LoadingState(Modifier.fillMaxSize())
        answeredQuestions.isNotEmpty() -> {
            val currentQ = answeredQuestions[currentQuestionIndex]
            QuizQuestionState(
                modifier = Modifier.fillMaxSize(),
                answeredQuestion = currentQ,
                questionNumber = currentQuestionIndex + 1,
                totalQuestions   = answeredQuestions.size,
                selectedAnswer   = selectedAnswer,
                onAnswerSelected = viewModel::selectAnswer,
                onNextQuestion   = viewModel::nextQuestion,
                onBackToInitial  = viewModel::resetQuiz
            )
        }
        else -> InitialState(
            modifier = Modifier.fillMaxSize(),
            onStartQuiz       = viewModel::startLoading,
            onNavigateToHistory = onNavigateToHistory
        )
    }
}
