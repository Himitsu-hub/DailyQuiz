package ru.alemak.dailyquiz_.presentation.quiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alemak.dailyquiz_.domain.model.Question
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor() : ViewModel() {

    // Состояние загрузки
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Навигация
    private val _navigateToReview = MutableStateFlow<Long?>(null)
    val navigateToReview: StateFlow<Long?> = _navigateToReview.asStateFlow()

    // Состояние викторины
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer: StateFlow<String?> = _selectedAnswer.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    fun startLoading() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(250) // Имитация загрузки
            _isLoading.value = false

            // Загружаем тестовые вопросы (замените на реальные данные)
            loadSampleQuestions()
        }
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun nextQuestion() {
        val currentIndex = _currentQuestionIndex.value
        val currentAnswer = _selectedAnswer.value
        val currentQuestion = _questions.value.getOrNull(currentIndex)

        // Проверяем ответ и увеличиваем счетчик если правильно
        if (currentAnswer == currentQuestion?.correctAnswer) {
            _score.value += 1
        }

        if (currentIndex < _questions.value.size - 1) {
            // Переходим к следующему вопросу
            _currentQuestionIndex.value = currentIndex + 1
            _selectedAnswer.value = null
        } else {
            // Викторина завершена, переходим к результатам
            completeQuiz()
        }
    }

    // Функция для сброса навигации после перехода
    fun onNavigationComplete() {
        _navigateToReview.value = null
    }

    fun resetQuiz() {
        _currentQuestionIndex.value = 0
        _selectedAnswer.value = null
        _score.value = 0
        _questions.value = emptyList()
    }

    private fun completeQuiz() {
        viewModelScope.launch {
            // Здесь можно сохранить результаты в БД
            _navigateToReview.value = 1L // ID завершенной викторины
        }
    }

    private fun loadSampleQuestions() {
        // Временные тестовые вопросы (замените на реальные данные из UseCase)
        val sampleQuestions = listOf(
            Question(
                id = "1",
                question = "Столица Франции?",
                correctAnswer = "Париж",
                incorrectAnswers = listOf("Лондон", "Берлин", "Мадрид"),
                category = "География",
                difficulty = "легкая"
            ),
            Question(
                id = "2",
                question = "2 + 2 = ?",
                correctAnswer = "4",
                incorrectAnswers = listOf("5", "3", "22"),
                category = "Математика",
                difficulty = "легкая"
            ),
            Question(
                id = "3",
                question = "Самый большой океан?",
                correctAnswer = "Тихий",
                incorrectAnswers = listOf("Атлантический", "Индийский", "Северный Ледовитый"),
                category = "География",
                difficulty = "средняя"
            )
        )
        _questions.value = sampleQuestions
    }
}