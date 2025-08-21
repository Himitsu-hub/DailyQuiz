package ru.alemak.dailyquiz_.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.alemak.dailyquiz_.data.local.entity.QuizResultEntity

@Dao
interface QuizResultDao {
    @Insert
    suspend fun insert(result: QuizResultEntity)

    @Query("SELECT * FROM quiz_results WHERE quizId = :quizId")
    suspend fun getByQuizId(quizId: Long): QuizResultEntity?

    @Query("SELECT * FROM quiz_results ORDER BY completedAt DESC")
    suspend fun getAll(): List<QuizResultEntity>

    @Query("DELETE FROM quiz_results")
    suspend fun deleteAll()
}