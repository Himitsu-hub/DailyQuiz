package ru.alemak.dailyquiz_.presentation.history

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.alemak.dailyquiz_.presentation.history.viewmodel.HistoryViewModel

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel, // Добавлен параметр viewModel
    onNavigateBack: () -> Unit,
    onNavigateToReview: (quizId: Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Экран истории",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onNavigateBack) {
            Text("Назад")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onNavigateToReview(2L) }) {
            Text("Разбор попытки (ID: 2)")
        }
    }
}

@Preview
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