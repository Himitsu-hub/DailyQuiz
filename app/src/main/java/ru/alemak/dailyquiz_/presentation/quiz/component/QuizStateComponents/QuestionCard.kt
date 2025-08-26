package ru.alemak.dailyquiz_.presentation.quiz.component.QuizStateComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alemak.dailyquiz_.domain.model.AnsweredQuestion
import ru.alemak.dailyquiz_.ui.theme.QuestionColor


@Composable
fun QuestionCard(
    answeredQuestion: AnsweredQuestion,   // ← новый параметр
    questionNumber: Int,
    totalQuestions: Int,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    onNextQuestion: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(top = 180.dp)
        .padding(horizontal = 20.dp)
){
    Card(
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(548.dp)
            .padding(horizontal = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            ) {
                Text(
                    text = "Вопрос $questionNumber из $totalQuestions",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = QuestionColor
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = answeredQuestion.question.question,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    maxLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 120.dp)
            ) {
                AnswersGrid(
                    options = answeredQuestion.options,
                    selectedAnswer = selectedAnswer,
                    onAnswerSelected = onAnswerSelected,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )
            }


            Button(
                onClick = onNextQuestion,
                enabled = selectedAnswer != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedAnswer != null) Color(0xFF6200EE) else Color(0xFFE0E0E0)
                )
            ) {
                Text(
                    text = "ДАЛЕЕ",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}