package ru.alemak.dailyquiz_.presentation.history

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alemak.dailyquiz_.R
import ru.alemak.dailyquiz_.domain.model.AnsweredQuestion
import ru.alemak.dailyquiz_.presentation.history.viewmodel.HistoryViewModel
import ru.alemak.dailyquiz_.ui.theme.BorderColor

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToReview: (quizId: Long) -> Unit,
    onStartQuiz: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF6200EE))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_back),
                contentDescription = "Arrow Back",
                modifier = Modifier
                    .clickable { onNavigateBack() }
                    .padding(start = 20.dp)
                    .size(24.dp)
            )

            TitleTextHistory(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.width(44.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 250.dp),
            contentAlignment = Alignment.Center
        ) {
            EmptyHistory(onStartQuiz = onStartQuiz)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            TitleTextQuiz()
        }
    }
}


@Composable
private fun TitleTextHistory(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        text = "История",
        fontSize = 32.sp,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
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
fun EmptyHistory(
    onStartQuiz: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .width(320.dp)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Вы ещё не проходили\nни одной викторины",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onStartQuiz,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
            ) {
                Text(
                    text = "НАЧАТЬ ВИКТОРИНУ",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}






    @Composable
    fun HistoryScreenPreview() {
        // Для превью создаем простой UI без ViewModel
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Экран истории (Preview)",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {}) {
                Text("Назад")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {}) {
                Text("Разбор попытки")
            }
        }
    }
