package ru.alemak.dailyquiz_.domain.model

data class AnsweredQuestion(
    val question: Question,           // исходный вопрос без изменений
    val options: List<String>,        // порядок, в котором показывали варианты
    val userAnswer: String? = null,   // null, если не ответили
    val isCorrect: Boolean = false
)