package ru.alemak.dailyquiz_.data.local

import androidx.room.TypeConverter
import ru.alemak.dailyquiz_.domain.model.Question

object Converters {

    @TypeConverter
    fun fromQuestions(questions: List<Question>): String {
        return questions.joinToString(";;") { q ->
            "${q.id}|${q.question}|${q.correctAnswer}|${q.incorrectAnswers.joinToString(",")}"
        }
    }

    @TypeConverter
    fun toQuestions(data: String): List<Question> {
        return data.split(";;").map { part ->
            val items = part.split('|')
            Question(
                id = items[0],
                question = items[1],
                correctAnswer = items[2],
                incorrectAnswers = items[3].split(',')
            )
        }
    }
}