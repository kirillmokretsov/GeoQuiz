package io.github.kirillmokretsov.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.kirillmokretsov.geoquiz.R
import com.google.android.material.snackbar.Snackbar

private const val KEY_IS_RESULT_SHOWN = "is_result_shown"
private const val KEY_CHEATS_LEFT = "cheats_left"
private const val EXTRA_ANSWER_IS_TRUE = "io.github.kirillmokretsov.geoquiz.answer_is_true"
private const val EXTRA_CHEATS_LEFT = "io.github.kirillmokretsov.geoquiz.chets_left"
const val EXTRA_ANSWER_SHOWN = "io.github.kirillmokretsov.geoquiz.answer_shown"

class CheatActivity : AppCompatActivity() {

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(CheatViewModel::class.java)
    }

    private lateinit var textViewAnswer: TextView
    private lateinit var textViewCheatsLeft: TextView
    private lateinit var buttonShowAnswer: Button

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean, cheatsLeft: Int): Intent =
            Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                putExtra(EXTRA_CHEATS_LEFT, cheatsLeft)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        Log.d("mylogs", "onCreate")

        cheatViewModel.answerIsTrue =
            intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, cheatViewModel.answerIsTrue)
        if (savedInstanceState == null)
        cheatViewModel.cheatsLeft =
            intent.getIntExtra(EXTRA_CHEATS_LEFT, cheatViewModel.cheatsLeft)
        else cheatViewModel.cheatsLeft = savedInstanceState.getInt(KEY_CHEATS_LEFT)
        cheatViewModel.isResultShown =
            savedInstanceState?.getBoolean(KEY_IS_RESULT_SHOWN, false) ?: false

        textViewAnswer = findViewById(R.id.text_view_answer)
        buttonShowAnswer = findViewById(R.id.button_show_answer)

        textViewCheatsLeft = findViewById(R.id.text_view_cheats_left)
        updateCheatsLeft()

        findViewById<TextView>(R.id.text_view_api).text =
            String.format(resources.getString(R.string.api_level), Build.VERSION.SDK_INT)

        buttonShowAnswer.setOnClickListener {
            if (cheatViewModel.cheatsLeft > 0 && !cheatViewModel.isResultShown) {
                cheatViewModel.isResultShown = true
                cheatViewModel.cheatsLeft--
                updateCheatsLeft()
                updateAnswer()
                setAnswerShownResult(true)
            } else {
                Snackbar.make(it, R.string.cheats_used, Snackbar.LENGTH_SHORT)
            }
        }

        if (cheatViewModel.isResultShown) {
            setAnswerShownResult(true)
            updateAnswer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("mylogs", "onSaveInstanceState()")
        outState.putBoolean(KEY_IS_RESULT_SHOWN, cheatViewModel.isResultShown)
        outState.putInt(KEY_CHEATS_LEFT, cheatViewModel.cheatsLeft)
    }

    private fun updateAnswer() = textViewAnswer.setText(
        when {
            cheatViewModel.answerIsTrue -> R.string.button_true
            else -> R.string.button_false
        }
    )

    private fun updateCheatsLeft() {
        textViewCheatsLeft.text =
            String.format(resources.getString(R.string.cheats_left), cheatViewModel.cheatsLeft)
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        setResult(Activity.RESULT_OK,
            Intent().apply {
                putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
            })
    }
}