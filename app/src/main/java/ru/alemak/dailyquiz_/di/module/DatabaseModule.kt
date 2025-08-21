package ru.alemak.dailyquiz_.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.alemak.dailyquiz_.data.local.dao.QuizResultDao
import ru.alemak.dailyquiz_.data.local.database.QuizDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideQuizDatabase(@ApplicationContext context: Context): QuizDatabase {
        return QuizDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideQuizResultDao(database: QuizDatabase): QuizResultDao {
        return database.quizResultDao()
    }
}