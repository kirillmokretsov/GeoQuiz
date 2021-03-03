package io.github.kirillmokretsov.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.kirillmokretsov.geoquiz.R

private const val KEY_IS_RESULT_SHOWN = "is_result_shown"
private const val EXTRA_ANSWER_IS_TRUE = "io.github.kirillmokretsov.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "io.github.kirillmokretsov.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(CheatViewModel::class.java)
    }

    private lateinit var textViewAnswer: TextView
    private lateinit var buttonShowAnswer: Button

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent =
            Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        cheatViewModel.answerIsTrue =
            intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, cheatViewModel.answerIsTrue)
        cheatViewModel.isResultShown = savedInstanceState?.getBoolean(KEY_IS_RESULT_SHOWN, false) ?: false

        textViewAnswer = findViewById(R.id.text_view_answer)
        buttonShowAnswer = findViewById(R.id.button_show_answer)

        findViewById<TextView>(R.id.text_view_api).text = String.format(resources.getString(R.string.api_level), Build.VERSION.SDK_INT)

        buttonShowAnswer.setOnClickListener {
            cheatViewModel.isResultShown = true
            updateAnswer()
            setAnswerShownResult(true)
        }

        if (cheatViewModel.isResultShown) {
            setAnswerShownResult(true)
            updateAnswer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_IS_RESULT_SHOWN, cheatViewModel.isResultShown)
    }

    private fun updateAnswer() = textViewAnswer.setText(
        when {
            cheatViewModel.answerIsTrue -> R.string.button_true
            else -> R.string.button_false
        }
    )

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        setResult(Activity.RESULT_OK,
            Intent().apply {
                putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
            })
    }
}