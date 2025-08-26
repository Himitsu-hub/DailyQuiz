package ru.alemak.dailyquiz_.domain.model

data class Question(
    val id: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val category: String = "",
    val difficulty: String = ""
)
