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
import ru.alemak.dailyquiz_.domain.model.QuizResult
import ru.alemak.dailyquiz_.domain.repository.QuizRepository
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _navigateToReview = MutableStateFlow<Long?>(null)
    val navigateToReview: StateFlow<Long?> = _navigateToReview.asStateFlow()

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer: StateFlow<String?> = _selectedAnswer.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    fun finishQuiz(totalQuestions: Int, onFinished: (Long) -> Unit) {
        viewModelScope.launch {
            val result = QuizResult(
                quizId = System.currentTimeMillis(),
                correctAnswers = _score.value,
                totalQuestions = totalQuestions,
                questions = emptyList(),
                completedAt = System.currentTimeMillis()
            )
            quizRepository.saveQuizResult(result)
            onFinished(result.quizId)
        }
    }

    fun startLoading() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val questions = quizRepository.fetchQuestions()
                _questions.value = questions
            } catch (e: Exception) {
                println("Ошибка загрузки вопросов: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun nextQuestion() {
        val currentIndex = _currentQuestionIndex.value
        val currentAnswer = _selectedAnswer.value
        val currentQuestion = _questions.value.getOrNull(currentIndex)

        if (currentAnswer == currentQuestion?.correctAnswer) {
            _score.value += 1
        }

        if (currentIndex < _questions.value.size - 1) {
            _currentQuestionIndex.value = currentIndex + 1
            _selectedAnswer.value = null
        } else {
            completeQuiz()
        }
    }

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
            val quizId = System.currentTimeMillis()
            println("QUIZ VM: Final score = ${_score.value}")
            println("QUIZ VM: Total questions = ${_questions.value.size}")

            val result = QuizResult(
                quizId = quizId,
                correctAnswers = _score.value,
                totalQuestions = _questions.value.size,
                questions = _questions.value,
                completedAt = System.currentTimeMillis()
            )

            println("QUIZ VM: Saving result: ${result.correctAnswers}/${result.totalQuestions}")
            quizRepository.saveQuizResult(result)
            _navigateToReview.value = quizId
        }
    }
}
