package ru.alemak.dailyquiz_.presentation.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.alemak.dailyquiz_.R
import ru.alemak.dailyquiz_.presentation.review.component.QuizCardStub
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

    val result = quizResult ?: return
    LaunchedEffect(result) {
        println("ReviewScreen: questions.size = ${result.answeredQuestions.size}")
    }
    val answeredQuestions = result.answeredQuestions
    val resultData = getResultData(result.correctAnswers, result.totalQuestions)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF6200EE))
            .padding(top = 40.dp, bottom = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            TitleTextReview(
                modifier = Modifier.padding(top = 80.dp)
            )
        }

        item {
            Result(
                correctAnswers = result.correctAnswers,
                totalQuestions = result.totalQuestions,
                resultData = resultData,
                onBackToInitial = onBackToInitial,
            )
        }


        items(result.answeredQuestions) { answered ->
            QuizCardStub(
                answeredQuestion = answered,
                questionNumber   = result.answeredQuestions.indexOf(answered) + 1,
                totalQuestions   = result.answeredQuestions.size
            )
        }
    }
}



data class ResultData(
    val title: String,
    val subtitle: String,
    val filledStars: Int
)

private fun getResultData(correct: Int, total: Int): ResultData {
    val percentage = correct.toFloat() / total.toFloat()

    return when {
        percentage == 1f -> ResultData(
            "Идеально!",
            "$correct/$total — вы ответили на всё правильно. Это блестящий результат!",
            5
        )
        percentage >= 0.8f -> ResultData(
            "Почти идеально!",
            "$correct/$total — очень близко к совершенству. Ещё один шаг!",
            4
        )
        percentage >= 0.6f -> ResultData(
            "Хороший результат!",
            "$correct/$total — вы на верном пути. Продолжайте тренироваться!",
            3
        )
        percentage >= 0.4f -> ResultData(
            "Есть над чем поработать",
            "$correct/$total — не расстраивайтесь, попробуйте ещё раз!",
            2
        )
        percentage >= 0.2f -> ResultData(
            "Сложный вопрос?",
            "$correct/$total — иногда просто не ваш день. Следующая попытка будет лучше!",
            1
        )
        else -> ResultData(
            "Бывает и так!",
            "$correct/$total — не отчаивайтесь. Начните заново!",
            0
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
            StarRating(
                filledStars = resultData.filledStars,
                totalStars = 5,
                starSize = 52.dp,
            )

            Text(
                text = "$correctAnswers из $totalQuestions",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFB800),
                textAlign = TextAlign.Center
            )


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



@Composable
fun StarRating(
    filledStars: Int,
    totalStars: Int = 5,
    starSize: Dp = 52.dp,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        repeat(totalStars) { index ->
            val starResource = if (index < filledStars) {
                R.drawable.star_filled
            } else {
                R.drawable.star_empty
            }

            Image(
                painter = painterResource(id = starResource),
                contentDescription = if (index < filledStars) "Filled star" else "Empty star",
                modifier = Modifier.size(starSize)
            )
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


