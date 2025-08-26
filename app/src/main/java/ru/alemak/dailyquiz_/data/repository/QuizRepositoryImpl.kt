package ru.alemak.dailyquiz_.data.repository

import ru.alemak.dailyquiz_.data.local.dao.QuizResultDao
import ru.alemak.dailyquiz_.data.local.entity.toDomain
import ru.alemak.dailyquiz_.data.local.entity.toEntity
import ru.alemak.dailyquiz_.domain.model.Question
import ru.alemak.dailyquiz_.domain.model.QuizResult
import ru.alemak.dailyquiz_.domain.repository.QuizRepository
import ru.alemak.dailyquiz_.domain.repository.RemoteQuizRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val quizResultDao: QuizResultDao,
    private val remoteQuizRepository: RemoteQuizRepository
) : QuizRepository {

    // --- ВОПРОСЫ ---
    override suspend fun fetchQuestions(): List<Question> {
        return remoteQuizRepository.fetchQuestions()
    }

    // --- РЕЗУЛЬТАТЫ ---
    override suspend fun getQuizResult(quizId: Long): QuizResult {
        println("REPO: Getting result for quizId: $quizId")
        val entity = quizResultDao.getByQuizId(quizId)

        if (entity == null) {
            println("REPO: Result not found in DB, creating default")
            return createDefaultResult(quizId)
        } else {
            println("REPO: Found result in DB: ${entity.correctAnswers}/${entity.totalQuestions}")
            return entity.toDomain()
        }
    }

    override suspend fun saveQuizResult(result: QuizResult) {
        println("REPO: Saving result to DB: ${result.quizId}, score: ${result.correctAnswers}/${result.totalQuestions}")
        quizResultDao.insert(result.toEntity())
    }

    override suspend fun getQuizHistory(): List<QuizResult> {
        return quizResultDao.getAll().map { it.toDomain() }
    }

    private fun createDefaultResult(quizId: Long): QuizResult {
        return QuizResult(
            quizId = quizId,
            correctAnswers = 0,
            totalQuestions = 0,
            questions = emptyList(),
            completedAt = quizId
        )
    }
}