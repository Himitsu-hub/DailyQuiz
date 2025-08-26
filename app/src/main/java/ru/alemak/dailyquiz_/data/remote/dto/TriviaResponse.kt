package ru.alemak.dailyquiz_.data.remote.dto

data class TriviaResponse(
    val results: List<TriviaQuestionDto>
)

data class TriviaQuestionDto(
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>,
    val category: String,
    val difficulty: String
)