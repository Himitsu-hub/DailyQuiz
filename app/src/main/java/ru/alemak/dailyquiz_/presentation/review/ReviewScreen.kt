package ru.alemak.dailyquiz_.presentation.review

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.alemak.dailyquiz_.presentation.review.viewmodel.ReviewViewModel

@Composable
fun ReviewScreen(
    viewModel: ReviewViewModel, // Ожидаем реальный ViewModel
    quizId: Long,
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Разбор викторины ID: $quizId",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onNavigateBack) {
            Text("Назад")
        }
    }
}

// УДАЛИТЕ этот класс - он не нужен!
// class PreviewReviewViewModel : ReviewViewModel()

@Preview
@Composable
fun ReviewScreenPreview() {
    // Для превью создаем простой UI без ViewModel
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