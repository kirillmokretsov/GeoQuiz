package io.github.kirillmokretsov.geoquiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.kirillmokretsov.geoquiz.R
import com.google.android.material.snackbar.Snackbar

private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(QuizViewModel::class.java)
    }

    private var completed = 0
    private var correctly = 0

    private lateinit var textViewQuestion: TextView
    private lateinit var buttonTrue: Button
    private lateinit var buttonFalse: Button
    private lateinit var buttonPrev: ImageButton
    private lateinit var buttonNext: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quizViewModel.currentQuestionIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0

        textViewQuestion = findViewById(R.id.text_view_question)
        buttonTrue = findViewById(R.id.button_true)
        buttonFalse = findViewById(R.id.button_false)
        buttonPrev = findViewById(R.id.button_prev)
        buttonNext = findViewById(R.id.button_next)

        buttonTrue.setOnClickListener {
            checkAnswer(true, it)
        }
        buttonFalse.setOnClickListener {
            checkAnswer(false, it)
        }
        buttonPrev.setOnClickListener {
            quizViewModel.moveBack()
            updateQuestion()
            isCompletedTest(it)
        }
        buttonNext.setOnClickListener {
            quizViewModel.moveForward()
            updateQuestion()
            isCompletedTest(it)
        }
        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentQuestionIndex)
    }

    private fun updateQuestion() = textViewQuestion.setText(quizViewModel.currentQuestionText)

    private fun checkAnswer(userAnswer: Boolean, view: View) {
        if (!quizViewModel.currentQuestionIsAnswered) {
            quizViewModel.currentQuestionIsAnswered = true
            completed++

            val messageResId = if (userAnswer == quizViewModel.currentQuestionAnswer) {
                correctly++
                R.string.answer_true
            } else {
                R.string.answer_false
            }

            Snackbar.make(view, messageResId, Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(view, R.string.answer_repeat, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun isCompletedTest(view: View) {
        if (completed == quizViewModel.questionBankSize) {
            var message = getString(R.string.result)
            message = message + " " + (correctly.toDouble() / completed.toDouble()) * 100 + '%'
            Snackbar.make(this, view, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}