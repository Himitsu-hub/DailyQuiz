package ru.alemak.dailyquiz_.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alemak.dailyquiz_.domain.model.QuizResult

@Entity(tableName = "quiz_results")
data class QuizResultEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quizId: Long,
    val correctAnswers: Int,
    val totalQuestions: Int,
    val completedAt: Long
)

fun QuizResultEntity.toDomain(): QuizResult {
    return QuizResult(
        quizId = this.quizId,
        correctAnswers = this.correctAnswers,
        totalQuestions = this.totalQuestions,
        questions = emptyList(),
        completedAt = this.completedAt
    )
}

fun QuizResult.toEntity(): QuizResultEntity {
    return QuizResultEntity(
        quizId = this.quizId,
        correctAnswers = this.correctAnswers,
        totalQuestions = this.totalQuestions,
        completedAt = this.completedAt
    )
}