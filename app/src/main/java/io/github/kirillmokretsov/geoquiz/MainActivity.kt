package io.github.kirillmokretsov.geoquiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.kirillmokretsov.geoquiz.R
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {

    private val tag = "my_logs"

    private lateinit var textViewQuestion: TextView
    private lateinit var buttonTrue: Button
    private lateinit var buttonFalse: Button
    private lateinit var buttonPrev: ImageButton
    private lateinit var buttonNext: ImageButton

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
            index = (index - 1) % questionBank.size
            if (index < 0) index = 7
            updateQuestion()
            isCompletedTest(it)
        }
        buttonNext.setOnClickListener {
            index = (index + 1) % questionBank.size
            updateQuestion()
            isCompletedTest(it)
        }
        textViewQuestion.setOnClickListener {
            index = (index + 1) % questionBank.size
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

    private fun updateQuestion() {
        val questionTextResId = questionBank[index].textResId
        textViewQuestion.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean, view: View) {
        if (!questionBank[index].isAnswered) {
            questionBank[index].isAnswered = true

            val correctAnswer = questionBank[index].answer

            questionBank[index].isAnswerTrue = userAnswer == correctAnswer
            val messageResId = if (questionBank[index].isAnswerTrue) {
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
        var completed: Int = 0;
        var correctly: Int = 0;
        for (question in questionBank) {
            if (question.isAnswered) {
                completed++
                if (question.isAnswerTrue)
                    correctly++
            }
        }
        if (completed == questionBank.size) {
            var message = getString(R.string.result)
            message = message + " " + (correctly.toDouble() / completed.toDouble()) * 100 + '%'
            Snackbar.make(this, view, message, Snackbar.LENGTH_SHORT).show()
        }
    }
}