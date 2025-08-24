package ru.alemak.dailyquiz_.presentation.review.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alemak.dailyquiz_.domain.model.Question
import ru.alemak.dailyquiz_.domain.model.QuizResult
import ru.alemak.dailyquiz_.domain.repository.QuizRepository
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val quizRepository: QuizRepository
) : ViewModel() {

    private val _quizResult = MutableStateFlow<QuizResult?>(null)
    val quizResult: StateFlow<QuizResult?> = _quizResult.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions.asStateFlow()
    fun loadQuizResult(quizId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val result = quizRepository.getQuizResult(quizId)
                _quizResult.value = result
                _questions.value = result.questions // ← Сохраняем вопросы
            } catch (e: Exception) {
                _error.value = "Не удалось загрузить результаты: ${e.message}"
                _quizResult.value = createFallbackResult(quizId)
                _questions.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun createFallbackResult(quizId: Long): QuizResult {
        return QuizResult(
            quizId = quizId,
            correctAnswers = 0,
            totalQuestions = 5,
            questions = emptyList(),
            completedAt = System.currentTimeMillis()
        )
    }

    fun restartQuiz() {
        _quizResult.value = null
    }

    fun getCorrectAnswersCount(): Int {
        return _quizResult.value?.correctAnswers ?: 0
    }

    fun getTotalQuestionsCount(): Int {
        return _quizResult.value?.totalQuestions ?: 0
    }
}