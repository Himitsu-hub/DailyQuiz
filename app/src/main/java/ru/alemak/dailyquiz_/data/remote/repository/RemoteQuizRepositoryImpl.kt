package ru.alemak.dailyquiz_.data.remote.repository

import androidx.core.text.HtmlCompat
import ru.alemak.dailyquiz_.data.remote.api.OpenTriviaApi
import ru.alemak.dailyquiz_.domain.model.Question
import ru.alemak.dailyquiz_.domain.repository.RemoteQuizRepository
import javax.inject.Inject

class RemoteQuizRepositoryImpl @Inject constructor(
    private val api: OpenTriviaApi
) : RemoteQuizRepository {

    override suspend fun fetchQuestions(): List<Question> {
        val response = api.getQuestions(amount = 5)
        return response.results.mapIndexed { index, dto ->
            Question(
                id = index.toString(),
                question = HtmlCompat.fromHtml(dto.question, HtmlCompat.FROM_HTML_MODE_COMPACT)
                    .toString(),
                correctAnswer = HtmlCompat.fromHtml(
                    dto.correct_answer,
                    HtmlCompat.FROM_HTML_MODE_COMPACT
                ).toString(),
                incorrectAnswers = dto.incorrect_answers.map {
                    HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_COMPACT).toString()
                },
                category = dto.category,
                difficulty = dto.difficulty
            )
        }
    }
}