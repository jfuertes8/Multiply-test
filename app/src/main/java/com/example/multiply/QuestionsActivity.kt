package com.example.multiply

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this,R.color.colorBackground)

        setContentView(R.layout.activity_questions)

        //get data from intent
        val intent = intent
        val tableNumber: Int = intent.getIntExtra("table", 0)

        /*Set title text of screen with chosen table*/
        table_selected.text = "You have selected the table for $tableNumber"

        /*Variable to know in which multiplication question we are*/
        var round = 1

        /*Function to paint each round's question. We call it here once to start the screen with the first question
        * already written*/
        printQuestion(tableNumber, round)

        //We store the number of correct answers from the user
        var correctAnswers: Int = 0

        //Variable to save the answer from user. It will later assign the input from the user
        var userAnswer = 0

        button_confirm.setOnClickListener {
            if (button_confirm.text == "CONFIRM") {
                //The machine calculates the correct answer and we store it in a variable
                var result = correctResult(tableNumber, round)

                /*We assign the value entered by the user to the previously declared variable userAnswer.
                 * In case the input received is a String or null, we set userAnswer equal to 0, so that the app
                 * doesn't crash*/
                try {
                    userAnswer = answer_text.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    userAnswer = 0
                }

                /*With if, we check if answer is correct or not, and show text and image*/
                if (answer_text.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please write your answer", Toast.LENGTH_SHORT).show()
                } else if (checkAnswer(userAnswer, result)) {
                    paintResult(true, result)
                    correctAnswers++
                    button_confirm.text = "NEXT"
                } else {
                    paintResult(false, result)
                    button_confirm.text = "NEXT"
                }

            } else if (button_confirm.text == "NEXT") {
                if (round <= 9) {
                    //Increase in 1 the round variable, print new question with updated round
                    round++
                    printQuestion(tableNumber, round)

                    //Quit the image and textAnswer preivously shown and written
                    imageView.setImageResource(0)
                    answer.text = ""

                    //Empty the user input space from the previously inserted text
                    answer_text.setText("")

                    //I automatically open the keyboard for each new question
                    openKeyboardAuto()

                    button_confirm.text = "CONFIRM"

                } else {
                    //Start new screen once the 10 questions have been completed
                    val intent = Intent(this, ResultScreenActivity::class.java)
                    intent.putExtra("correctAnswers", correctAnswers)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    //Function that prints the question for each round
    private fun printQuestion(tableNumber: Int, round: Int) {
        question_text.text = "$tableNumber x $round = "
    }

    //Function that calculates the correct answer for each question
    private fun correctResult(tableNumber: Int, round: Int): Int {
        return tableNumber * round

    }

    //Function that compares the correct answer with the one given by the user
    private fun checkAnswer(userAnswer: Int, result: Int): Boolean {
        return (userAnswer == result)
    }

    //Function that shows the image and text corresponding to whether user answered right or wrong
    private fun paintResult(c: Boolean, result: Int) {
        if (c) {
            imageView.setImageResource(R.drawable.right)
            answer.text = "Correct!"
        } else {
            imageView.setImageResource(R.drawable.wrong)
            answer.text = "Sorry. The right answer was $result"
        }
    }

    //Function that opens the keyboard for each new question
    private fun openKeyboardAuto() {
        answer_text.requestFocus()
        answer_text.isFocusableInTouchMode = true

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(
            answer_text, InputMethodManager.SHOW_FORCED
        )
    }
}
