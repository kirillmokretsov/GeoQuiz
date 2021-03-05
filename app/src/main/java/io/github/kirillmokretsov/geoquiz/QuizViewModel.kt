package io.github.kirillmokretsov.geoquiz

import androidx.lifecycle.ViewModel
import com.github.kirillmokretsov.geoquiz.R

class QuizViewModel : ViewModel() {

    val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_russia, true),
        Question(R.string.question_europe, true),
        Question(R.string.question_america, false),
        Question(R.string.question_asia, true),
        Question(R.string.question_barbados, true),
        Question(R.string.question_lake, true),
        Question(R.string.question_question, true)
    )
    private var index = 0
    var completed = 0
    var correctly = 0
    var cheatsLeft = 3

    val questionBankSize: Int
        get() = questionBank.size

    var currentQuestionIndex: Int
        get() = index
        set(value) {
            index = value
        }

    val currentQuestionAnswer: Boolean
        get() = questionBank[index].answer

    val currentQuestionText: Int
        get() = questionBank[index].textResId

    var currentQuestionIsAnswered: Boolean
        get() = questionBank[index].isAnswered
        set(value) {
            questionBank[index].isAnswered = value
        }

    var currentIsCheater: Boolean
        get() = questionBank[index].isCheated
        set(value) {
            questionBank[index].isCheated = value
        }

    fun moveForward() {
        index = (index + 1) % questionBank.size
    }

    fun moveBack() {
        index = (index - 1) % questionBank.size
        if (index < 0) index = questionBank.size - 1
    }

}