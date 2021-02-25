package com.github.kirillmokretsov.geoquiz

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var buttonTrue: Button
    private lateinit var buttonFalse: Button

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_russia, true),
        Question(R.string.question_europe, true),
        Question(R.string.question_america, true),
        Question(R.string.question_asia, true),
        Question(R.string.question_barbados, true),
        Question(R.string.question_lake, true),
        Question(R.string.question_question, true)
    )
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonTrue = findViewById(R.id.button_true)
        buttonFalse = findViewById(R.id.button_false)

        buttonTrue.setOnClickListener {
            Snackbar.make(it, R.string.answer_true, Snackbar.LENGTH_SHORT).show()
        }
        buttonFalse.setOnClickListener {
            Snackbar.make(it, R.string.answer_false, Snackbar.LENGTH_SHORT).show()
        }
    }
}