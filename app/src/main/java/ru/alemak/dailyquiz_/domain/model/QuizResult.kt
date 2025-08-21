package ru.alemak.dailyquiz_.domain.model

data class QuizResult(
    val quizId: Long,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val questions: List<QuestionResult>,
    val completedAt: Long
)

data class QuestionResult(
    val questionId: Long,
    val questionText: String,
    val correctAnswer: String,
    val userAnswer: String,
    val isCorrect: Boolean
)