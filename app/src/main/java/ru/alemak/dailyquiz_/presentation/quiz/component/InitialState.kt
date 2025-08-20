package ru.alemak.dailyquiz_.presentation.quiz.component
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alemak.dailyquiz_.R

@Composable
fun InitialState(
    modifier: Modifier = Modifier,
    onStartQuiz: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF6200EE)),
            contentAlignment = Alignment.Center
    ) {
        HistoryButton(
            onClick = onNavigateToHistory,
            modifier = Modifier
                .wrapContentSize()
                .padding(top = 50.dp)
                .align(Alignment.TopCenter)
        )

        TitleText(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 300.dp)



        )

        WelcomeCard(
            onStartQuiz = onStartQuiz,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 340.dp)
        )
    }
}

@Composable
private fun HistoryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.clickable { onClick() }.padding(top = 80.dp),

    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "История",
                color = Color(0xFF6200EE),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Image(
                 painter = painterResource(id = R.drawable.history_icon),
                 contentDescription = "History Icon",
                 modifier = Modifier.size(20.dp),
                 colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color(0xFF6200EE))
             )
        }
    }
}

@Composable
fun TitleText(
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.dailyquiz),
        contentDescription = "Daily Quiz",
        modifier = modifier.size(width = 280.dp, height = 63.16.dp)
    )
}

@Composable
private fun WelcomeCard(
    onStartQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.size(width = 320.dp, height = 206.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = "Добро пожаловать\nв DailyQuiz!",
                textAlign = TextAlign.Center,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
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


