package com.example.multiply

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_questions.*

class QuestionsActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
                //The machine calculates the correct answer and stores it in a variable
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
                if(answer_text.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please write your answer", Toast.LENGTH_SHORT).show()
                } else if (checkAnswer(userAnswer, result)) {
                    paintResult(true, result)
                    correctAnswers++
                } else {
                    paintResult(false, result)
                }

                button_confirm.text = "NEXT"

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

                    //Abro automáticamente el teclado para la siguiente pregunta
                    openKeyboardAuto()

                    button_confirm.text = "CONFIRM"

                } else {
                    //Start new screen once the 10 questions have been completed
                    val intent = Intent(this, ResultScreenActivity::class.java)
                    intent.putExtra("correctAnswers",  correctAnswers)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    //Función que imprime en pantalla la pregunta (los dos numeros a multiplicar y el simbolo = )
    private fun printQuestion(tableNumber: Int, round: Int) {
        question_text.text = "$tableNumber x $round = "
    }

    //Función que calcula el resultado correcto para cada pregunta
    private fun correctResult(tableNumber: Int, round: Int): Int {
        return tableNumber * round

    }

    //Función que compara el resultado correcto con el input introducido por el usuari
    private fun checkAnswer(userAnswer: Int, result: Int): Boolean {
        return (userAnswer == result)
    }

    //Función que muestra la imagen y el texto correspondientes dependiendo de si ha acertado o fallado
    private fun paintResult(c: Boolean, result: Int) {
        if (c) {
            imageView.setImageResource(R.drawable.right)
            answer.text = "Correct!"
        } else {
            imageView.setImageResource(R.drawable.wrong)
            answer.text = "Sorry. The right answer was $result"
        }
    }

    //Función que abre el teclado automáticamente cuando paso a cada pregunta
    private fun openKeyboardAuto() {
        answer_text.requestFocus()
        answer_text.isFocusableInTouchMode = true

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(
            answer_text, InputMethodManager.SHOW_FORCED
        )
    }
}
