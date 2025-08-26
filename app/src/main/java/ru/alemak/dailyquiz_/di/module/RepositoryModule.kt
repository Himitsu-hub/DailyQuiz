package ru.alemak.dailyquiz_.di.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alemak.dailyquiz_.data.remote.repository.RemoteQuizRepositoryImpl
import ru.alemak.dailyquiz_.data.repository.QuizRepositoryImpl
import ru.alemak.dailyquiz_.domain.repository.QuizRepository
import ru.alemak.dailyquiz_.domain.repository.RemoteQuizRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    // Сохраняем уже существующий bind
    @Binds
    @Singleton
    abstract fun bindQuizRepository(
        quizRepositoryImpl: QuizRepositoryImpl
    ): QuizRepository

    // Добавляем новый bind
    @Binds
    @Singleton
    abstract fun bindRemoteQuizRepository(
        remoteQuizRepositoryImpl: RemoteQuizRepositoryImpl
    ): RemoteQuizRepository
}