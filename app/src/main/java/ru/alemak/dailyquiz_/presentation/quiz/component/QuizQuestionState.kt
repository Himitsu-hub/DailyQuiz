package ru.alemak.dailyquiz_.presentation.quiz.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alemak.dailyquiz_.R
import ru.alemak.dailyquiz_.domain.model.Question
import ru.alemak.dailyquiz_.presentation.quiz.component.QuizStateComponents.QuestionCard



@Composable
fun QuizQuestionState(
    modifier: Modifier = Modifier,
    question: Question,
    questionNumber: Int,
    totalQuestions: Int,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    onNextQuestion: () -> Unit,
    onBackToInitial: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF6200EE))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .clickable {
                        onBackToInitial()
                    }
                    .padding(start = 20.dp)
                    .size(24.dp)
            )

            TitleTextQuiz(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )


            Spacer(
                modifier = Modifier
                    .size(50.dp)
                    .padding(end = 50.dp)
            )
        }


        QuestionCard(
            question = question,
            questionNumber = questionNumber,
            totalQuestions = totalQuestions,
            selectedAnswer = selectedAnswer,
            onAnswerSelected = onAnswerSelected,
            onNextQuestion = onNextQuestion,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 190.dp)

        )

        FooterText(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 175.dp)
        )
    }
}

@Composable
private fun TitleTextQuiz(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.dailyquiz),
        contentDescription = "Daily Quiz",
        modifier = modifier.size(width = 180.dp, height = 40.6.dp)
    )
}

@Composable
private fun FooterText(modifier: Modifier = Modifier) {
    Text(
        text = "Вернуться к предыдущим вопросам нельзя",
        color = Color.White.copy(alpha = 0.7f),
        fontSize = 12.sp,
        modifier = modifier
    )
}