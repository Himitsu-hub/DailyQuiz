package ru.alemak.dailyquiz_.presentation.review.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alemak.dailyquiz_.R
import ru.alemak.dailyquiz_.domain.model.AnsweredQuestion
import ru.alemak.dailyquiz_.domain.model.Question


@Composable
fun QuizCardStub(
    answeredQuestion: AnsweredQuestion,
    questionNumber: Int,
    totalQuestions: Int,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        println("QuizCardStub: ${answeredQuestion.question.question}")
        println("QuizCardStub options:")
        answeredQuestion.options.forEachIndexed { i, s ->
            println("${i + 1}. $s")
        }
    }
    val allAnswers = answeredQuestion.options

    Card(
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .width(320.dp)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row {
                Text(
                    text = "Вопрос $questionNumber из $totalQuestions",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

                Image(
                    painter = painterResource(
                        id = if (answeredQuestion.question.correctAnswer == answeredQuestion.userAnswer){ R.drawable.correct_answer}
                        else {R.drawable.incorrect_answer}),
                    contentDescription = if (answeredQuestion.question.correctAnswer == answeredQuestion.userAnswer){"Correct Answer"}
                    else{"Incorrect answer"},
                    modifier = Modifier.padding(start = 140.dp).size(20.dp)
                )

            }

            Text(
                text = answeredQuestion.question.question,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            allAnswers.forEach { answer ->

                val isSelected = answer == answeredQuestion.userAnswer
                val isCorrect  = answer == answeredQuestion.question.correctAnswer
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            isSelected -> Color(0xFFFFFFFF)
                            else -> Color(0xFFF3F3F3)
                        }
                    ),
                    border = BorderStroke(
                        1.dp,
                        color = when {
                                isSelected && isCorrect -> Color(0xFF00AE3A)
                                isSelected && !isCorrect -> Color(0xFFE70000)
                                else -> Color(0xFFF5F5F5)
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 12.dp)
                    ) {
                        Image(
                            painter = painterResource(
                                id = when {
                                    isSelected && isCorrect-> R.drawable.correct_answer
                                    isSelected && !isCorrect -> R.drawable.incorrect_answer
                                    else -> R.drawable.radio_button_default
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .size(20.dp)
                        )
                        Text(
                            text = answer,
                            fontSize = 16.sp,
                            color = Color.Black,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}