package ru.alemak.dailyquiz_.domain.repository

import ru.alemak.dailyquiz_.domain.model.QuizResult

interface QuizRepository {
    suspend fun getQuizResult(quizId: Long): QuizResult
    suspend fun saveQuizResult(result: QuizResult)
    suspend fun getQuizHistory(): List<QuizResult>
}