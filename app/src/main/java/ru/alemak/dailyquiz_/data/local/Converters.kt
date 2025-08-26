package ru.alemak.dailyquiz_.data.local

import androidx.room.TypeConverter
import ru.alemak.dailyquiz_.domain.model.AnsweredQuestion
import ru.alemak.dailyquiz_.domain.model.Question



object Converters {

    private const val SEP_Q      = "|"
    private const val SEP_LIST   = ";;"
    private const val SEP_OPT    = "§§"      // ← новый разделитель для options
    private const val SEP_RECORD = "@@@"

    @TypeConverter
    fun fromAnsweredQuestions(list: List<AnsweredQuestion>): String =
        list.joinToString(SEP_LIST) { aq ->
            val q      = aq.question
            val qPart  = listOf(q.id, q.question, q.correctAnswer,
                q.incorrectAnswers.joinToString(SEP_OPT),
                q.category, q.difficulty).joinToString(SEP_Q)

            val optPart = aq.options.joinToString(SEP_OPT)
            val userPart = aq.userAnswer ?: ""
            val correctFlag = if (aq.isCorrect) "1" else "0"

            listOf(qPart, optPart, userPart, correctFlag).joinToString(SEP_RECORD)
        }

    @TypeConverter
    fun toAnsweredQuestions(data: String): List<AnsweredQuestion> {
        if (data.isBlank()) return emptyList()
        return data.split(SEP_LIST).map { part ->
            val (qPart, optPart, userPart, correctFlag) = part.split(SEP_RECORD, limit = 4)

            val qChunks = qPart.split(SEP_Q)
            val q = Question(
                id              = qChunks[0],
                question        = qChunks[1],
                correctAnswer   = qChunks[2],
                incorrectAnswers= qChunks[3].split(SEP_OPT),
                category        = qChunks.getOrElse(4) { "" },
                difficulty      = qChunks.getOrElse(5) { "" }
            )

            AnsweredQuestion(
                question  = q,
                options   = optPart.split(SEP_OPT),
                userAnswer= userPart.takeIf { it.isNotEmpty() },
                isCorrect = correctFlag == "1"
            )
        }
    }
}