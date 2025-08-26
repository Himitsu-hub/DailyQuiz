package ru.alemak.dailyquiz_.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alemak.dailyquiz_.data.local.Converters
import ru.alemak.dailyquiz_.domain.model.Question
import ru.alemak.dailyquiz_.domain.model.QuizResult

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quizId: Long,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val answeredQuestions: String,   // ← было questions
    val completedAt: Long
)

fun QuizResultEntity.toDomain(): QuizResult {
    val answeredQuestions = Converters.toAnsweredQuestions(this.answeredQuestions)
    return QuizResult(
        quizId = quizId,
        correctAnswers = correctAnswers,
        totalQuestions = totalQuestions,
        answeredQuestions = answeredQuestions,
        completedAt = completedAt
    )
}

fun QuizResult.toEntity(): QuizResultEntity {
    val answeredQuestionsStr = Converters.fromAnsweredQuestions(this.answeredQuestions)
    return QuizResultEntity(
        quizId = quizId,
        correctAnswers = correctAnswers,
        totalQuestions = totalQuestions,
        answeredQuestions = answeredQuestionsStr,
        completedAt = completedAt
    )
}