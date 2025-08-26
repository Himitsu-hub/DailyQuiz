package ru.alemak.dailyquiz_.domain.repository

import ru.alemak.dailyquiz_.domain.model.Question
import ru.alemak.dailyquiz_.domain.model.QuizResult

interface QuizRepository {
    suspend fun fetchQuestions(): List<Question>
    suspend fun saveQuizResult(result: QuizResult)
    suspend fun getQuizResult(quizId: Long): QuizResult
    suspend fun getQuizHistory(): List<QuizResult>
}