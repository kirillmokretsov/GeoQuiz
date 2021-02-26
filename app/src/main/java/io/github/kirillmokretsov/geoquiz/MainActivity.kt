package io.github.kirillmokretsov.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.kirillmokretsov.geoquiz.R
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    companion object {
        const val tag = "my_logs"
    }

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
        Log.d(tag, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        isCompletedTest(buttonFalse)
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "onRestart()")
    }

    private fun updateQuestion() = textViewQuestion.setText(quizViewModel.currentQuestionText)


    private fun checkAnswer(userAnswer: Boolean, view: View) {
        if (!quizViewModel.currentQuestionIsAnswered) {
            quizViewModel.currentQuestionIsAnswered = true
            completed++

            val correctAnswer = quizViewModel.currentQuestionAnswer

            quizViewModel.currentQuestionIsAnswerTrue = userAnswer == correctAnswer
            val messageResId = if (quizViewModel.currentQuestionIsAnswerTrue) {
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