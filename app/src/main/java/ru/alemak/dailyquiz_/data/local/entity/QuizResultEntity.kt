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
    val questions: String, // ← одна строка
    val completedAt: Long
)

fun QuizResultEntity.toDomain(): QuizResult {
    val questions = Converters.toQuestions(this.questions)
    return QuizResult(
        quizId = quizId,
        correctAnswers = correctAnswers,
        totalQuestions = totalQuestions,
        questions = questions,
        completedAt = completedAt
    )
}

fun QuizResult.toEntity(): QuizResultEntity {
    val questionsStr = Converters.fromQuestions(this.questions)
    return QuizResultEntity(
        quizId = quizId,
        correctAnswers = correctAnswers,
        totalQuestions = totalQuestions,
        questions = questionsStr,
        completedAt = completedAt
    )
}