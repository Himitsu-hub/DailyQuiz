package ru.alemak.dailyquiz_.domain.repository

import ru.alemak.dailyquiz_.domain.model.Question

interface RemoteQuizRepository {
    suspend fun fetchQuestions(): List<Question>
}