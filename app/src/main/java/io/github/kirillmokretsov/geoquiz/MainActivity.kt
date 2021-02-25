package io.github.kirillmokretsov.geoquiz

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.kirillmokretsov.geoquiz.R
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var textViewQuestion: TextView
    private lateinit var buttonTrue: Button
    private lateinit var buttonFalse: Button
    private  lateinit var buttonPrev: Button
    private lateinit var buttonNext: Button

    private val questionBank = listOf(
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

    override fun onCreate(savedInstanceState: Bundle?) {
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
            index = (index - 1) % questionBank.size
            updateQuestion()
        }
        buttonNext.setOnClickListener {
            index = (index + 1) % questionBank.size
            updateQuestion()
        }
        textViewQuestion.setOnClickListener {
            index = (index + 1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[index].textResId
        textViewQuestion.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean, view: View) {
        val correctAnswer = questionBank[index].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.answer_true
        } else {
            R.string.answer_false
        }

        Snackbar.make(view, messageResId, Snackbar.LENGTH_SHORT).show()

    }
}