package ru.alemak.dailyquiz_.presentation.quiz.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.alemak.dailyquiz_.domain.model.AnsweredQuestion
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


    private val _answeredQuestions = MutableStateFlow<List<AnsweredQuestion>>(emptyList())
    val answeredQuestions: StateFlow<List<AnsweredQuestion>> = _answeredQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex.asStateFlow()

    private val _selectedAnswer = MutableStateFlow<String?>(null)
    val selectedAnswer: StateFlow<String?> = _selectedAnswer.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score: StateFlow<Int> = _score.asStateFlow()

    fun startLoading() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val rawQuestions = quizRepository.fetchQuestions()
                _answeredQuestions.value = rawQuestions.map { q ->
                    val options = (listOf(q.correctAnswer) + q.incorrectAnswers).shuffled()
                    AnsweredQuestion(
                        question = q,
                        options = options
                    )
                }
            } catch (e: Exception) {
                println("Ошибка загрузки вопросов: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetAndStartQuiz() {
        _currentQuestionIndex.value = 0
        _score.value = 0
        _selectedAnswer.value = null
        _answeredQuestions.value = emptyList()
        startLoading()
    }
    fun selectAnswer(answer: String) {
        _selectedAnswer.value = answer
    }

    fun nextQuestion() {
        val currentIndex = _currentQuestionIndex.value
        val currentAnswer = _selectedAnswer.value ?: return

        val updated = _answeredQuestions.value.toMutableList()
        val old = updated[currentIndex]
        updated[currentIndex] = old.copy(
            userAnswer = currentAnswer,
            isCorrect = currentAnswer == old.question.correctAnswer
        )
        _answeredQuestions.value = updated

        if (currentAnswer == old.question.correctAnswer) {
            _score.value += 1
        }

        if (currentIndex < _answeredQuestions.value.size - 1) {
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
        _answeredQuestions.value = emptyList()
    }

    private fun completeQuiz() {
        viewModelScope.launch {
            val quizId = System.currentTimeMillis()
            val result = QuizResult(
                quizId = quizId,
                correctAnswers = _score.value,
                totalQuestions = _answeredQuestions.value.size,
                answeredQuestions = _answeredQuestions.value,
                completedAt = System.currentTimeMillis()
            )
            println("QUIZ VM: Saving result: ${result.correctAnswers}/${result.totalQuestions}")
            quizRepository.saveQuizResult(result)
            _navigateToReview.value = quizId
        }
    }
    }