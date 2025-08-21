package ru.alemak.dailyquiz_.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.alemak.dailyquiz_.data.repository.QuizRepositoryImpl
import ru.alemak.dailyquiz_.domain.repository.QuizRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideQuizRepository(impl: QuizRepositoryImpl): QuizRepository {
        return impl
    }
}