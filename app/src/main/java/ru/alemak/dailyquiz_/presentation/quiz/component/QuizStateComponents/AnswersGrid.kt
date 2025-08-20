package ru.alemak.dailyquiz_.presentation.quiz.component.QuizStateComponents

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.size.Size
import ru.alemak.dailyquiz_.R
import ru.alemak.dailyquiz_.domain.model.Question
import ru.alemak.dailyquiz_.ui.theme.BorderColor

@Composable
fun AnswersGrid(
    question: Question,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit
) {
    val allAnswers = remember(question) {
        (question.incorrectAnswers + question.correctAnswer).shuffled()
    }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        allAnswers.forEach { answer ->
            AnswerItem(
                answer = answer,
                isSelected = selectedAnswer == answer,
                onClick = { onAnswerSelected(answer) }
            )
        }
    }
}

@Composable
fun AnswerItem(
    answer: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color.White else Color(0xFFF5F5F5)
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = if (isSelected) BorderColor else Color(0xFFE0E0E0)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(
                    id = if (isSelected){ R.drawable.radio_button_selected}
                    else {R.drawable.radio_button_default}),
                contentDescription = if (isSelected){"Is Selected"}
                    else{"Not selected"},
                modifier = Modifier.padding(start = 10.dp, end = 10.dp).size(20.dp)
            )

            Text(
                text = answer,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}