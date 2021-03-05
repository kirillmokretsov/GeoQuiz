package io.github.kirillmokretsov.geoquiz

import androidx.lifecycle.ViewModel

class CheatViewModel : ViewModel() {
    var answerIsTrue = false
    var isResultShown = false
    var cheatsLeft = 3
}