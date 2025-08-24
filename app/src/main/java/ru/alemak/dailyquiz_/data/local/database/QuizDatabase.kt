package ru.alemak.dailyquiz_.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.alemak.dailyquiz_.data.local.dao.QuizResultDao
import ru.alemak.dailyquiz_.data.local.entity.QuizResultEntity

@Database(
    entities = [QuizResultEntity::class],
    version = 2,
    exportSchema = false
)
abstract class QuizDatabase : RoomDatabase() {
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        @Volatile
        private var Instance: QuizDatabase? = null

        fun getDatabase(context: Context): QuizDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    QuizDatabase::class.java,
                    "quiz_database"
                )
                    .fallbackToDestructiveMigration()   // ← добавь эту строку
                    .build()
            }
        }
    }
}