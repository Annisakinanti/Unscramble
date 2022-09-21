package com.example.android.unscramble.ui.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private var _score = 0
    val score: Int get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambleWord: String get() = _currentScrambledWord

    private var usedWordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    private var _count = 0
    val count: Int get() = _count

    init {
        getNextWord()
    }

    /*
     * Memperbarui currentWord dan currentScrambledWord dengan kata berikutnya.
     */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val scrambledWord = currentWord.toCharArray()
        scrambledWord.shuffle()
        if (usedWordsList.contains(currentWord)) {
            getNextWord()
        } else {
            while (scrambledWord.toString().equals(currentWord, false)) {
                scrambledWord.shuffle()
            }
            _currentScrambledWord = String(scrambledWord)
            ++_currentWordCount
            usedWordsList.add(currentWord)
        }
    }

    private fun increaseScore(){
        _score += SCORE_INCREASE
    }

    fun isUserWordCorrect(playerWord : String) : Boolean{
        if(playerWord.equals(currentWord)){
            increaseScore()
            return true
        }else{
            return false
        }
    }
    /*
     * Mengembalikan true jika jumlah kata saat ini kurang dari MAX_NO_OF_WORDS.
     * Memperbarui kata berikutnya.
     */
    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else {
            false
        }
    }

    /*
     * Menginisialisasi ulang data game untuk memulai ulang game.
     */
    fun reinitializeData(){
        _score = 0
        _currentWordCount = 0
        usedWordsList.clear()
        getNextWord()
    }
}