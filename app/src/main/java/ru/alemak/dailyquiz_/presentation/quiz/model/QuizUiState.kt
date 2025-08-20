package ru.alemak.dailyquiz_.presentation.quiz.model

import ru.alemak.dailyquiz_.domain.model.Question

data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswer: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
