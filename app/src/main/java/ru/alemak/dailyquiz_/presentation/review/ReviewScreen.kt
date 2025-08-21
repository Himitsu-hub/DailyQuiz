package ru.alemak.dailyquiz_.presentation.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.alemak.dailyquiz_.presentation.review.viewmodel.ReviewViewModel

@Composable
fun ReviewScreen(
    modifier: Modifier = Modifier,
    viewModel: ReviewViewModel = hiltViewModel(),
    quizId: Long,
    onBackToInitial: () -> Unit
) {
    val quizResult by viewModel.quizResult.collectAsState()

    LaunchedEffect(quizId) {
        viewModel.loadQuizResult(quizId)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF6200EE))
    ) {
        val result = quizResult ?: return@Box

        val resultData = getResultData(result.correctAnswers, result.totalQuestions)

        TitleTextReview(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 225.dp)
        )

        Result(
            modifier = Modifier.align(Alignment.Center),
            correctAnswers = result.correctAnswers,
            totalQuestions = result.totalQuestions,
            resultData = resultData,
            onBackToInitial = onBackToInitial,
        )
    }
}
data class ResultData(
    val title: String,
    val subtitle: String
)

private fun getResultData(correct: Int, total: Int): ResultData {
    val percentage = correct.toFloat() / total.toFloat()

    return when {
        percentage == 1f -> ResultData(
            "Идеально!",
            "$correct/$total — вы ответили на всё правильно. Это блестящий результат!"
        )
        percentage >= 0.8f -> ResultData(
            "Почти идеально!",
            "$correct/$total — очень близко к совершенству. Ещё один шаг!"
        )
        percentage >= 0.6f -> ResultData(
            "Хороший результат!",
            "$correct/$total — вы на верном пути. Продолжайте тренироваться!"
        )
        percentage >= 0.4f -> ResultData(
            "Есть над чем поработать",
            "$correct/$total — не расстраивайтесь, попробуйте ещё раз!"
        )
        percentage >= 0.2f -> ResultData(
            "Сложный вопрос?",
            "$correct/$total — иногда просто не ваш день. Следующая попытка будет лучше!"
        )
        else -> ResultData(
            "Бывает и так!",
            "$correct/$total — не отчаивайтесь. Начните заново!"
        )
    }
}
@Composable
private fun TitleTextReview(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        text = "Результаты",
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}

@Composable
private fun Result(
    modifier: Modifier = Modifier,
    correctAnswers: Int,
    totalQuestions: Int,
    resultData: ResultData,
    onBackToInitial: () -> Unit
) {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "$correctAnswers из $totalQuestions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFB800),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = resultData.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = resultData.subtitle,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onBackToInitial,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE)
                )
            ) {
                Text(
                    text = "НАЧАТЬ ЗАНОВО",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Разбор викторины (Preview)",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {}) {
            Text("Назад")
        }
    }
}