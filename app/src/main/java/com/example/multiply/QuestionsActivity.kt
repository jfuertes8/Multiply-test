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
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackgroundTest)

        setContentView(R.layout.activity_questions)

        //get data from intent
        val intent = intent
        val tableNumber: Int = intent.getIntExtra("table", 0)
        val chosenQuestions: Int = intent.getIntExtra("chosenQuestions", 10)
        val practice: Boolean = intent.getBooleanExtra("practice", true)

        if (!practice) {
            activity_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorBackgroundTest))
            button_confirm.setBackgroundResource(R.drawable.custom_button__test_filled)
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackgroundTest)
        } else {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)
        }

        //Get questions from Constants
        val questions = Constants.getQuestions(practice, tableNumber, chosenQuestions)


        /*Set title text of screen with chosen table*/
        table_selected.text = "You have selected the table for $tableNumber"

        /*Variable to know in which multiplication question we are*/
        var round = 0

        /*Function to paint each round's question. We call it here once to start the screen with the first question
        * already written*/
        printQuestion(round, questions)

        //We store the number of correct answers from the user
        var correctAnswers: Int = 0

        //Variable to save the answer from user. It will later assign the input from the user
        var userAnswer = 0

        var step = 0

        button_confirm.setOnClickListener {
            if (answer_text.text.toString().isEmpty()) {
                //We show a toast if the user didn't input anything in the textfield
                Toast.makeText(this, "Please write your answer", Toast.LENGTH_SHORT).show()
            } else {
                //If we reach the final round, we jump to the next activity
                if (round > questions.size - 1) {
                    val totalQuestions = questions.size
                    val intent = Intent(this, ResultScreenActivity::class.java)
                    intent.putExtra("correctAnswers", correctAnswers)
                    intent.putExtra("totalQuestions", totalQuestions)
                    startActivity(intent)
                    finish()
                } else {
                    //If we haven't reached the final round we keep on checking and moving forward
                    if (step == 0) {

                        //We save on a variable the boolean of whether the user input is true or false
                        val c = questions[round].check(answer_text.text.toString().toInt())

                        //If true, we increase in one the number of correct answers
                        if (c) correctAnswers++

                        //We paint the "right" or "wrong" image and the text below it
                        paintResult(c, questions[round].getResult())

                        printNextButton(round, questions)

                        step = 1

                        //We increase round by 1
                        round++

                    } else {

                        //We print the new question again
                        printQuestion(round, questions)

                        //Quit the image and textAnswer preivously shown and written
                        imageView.setImageResource(0)
                        answer.text = ""

                        //Empty the user input space from the previously inserted text
                        answer_text.setText("")

                        //I automatically open the keyboard for each new question
                        openKeyboardAuto()

                        button_confirm.text = "CHECK ANSWER"
                        step = 0
                    }
                }
            }
        }
    }

    //FUNCTIONS---------------------------------------------------------------------------------------------------

    //Function that prints the question for each round
    @SuppressLint("SetTextI18n")
    private fun printQuestion(round: Int, questions: ArrayList<Question>) {
        question_text.text =
            questions[round].num1.toString() + " x " + questions[round].num2.toString() + " = "
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
    //Function which changes the key printed on the button after completing final round
    private fun printNextButton(round: Int, questions: ArrayList<Question>) {
        if(round == questions.size - 1) {
            button_confirm.text = "SEE RESULTS"
        } else {
            button_confirm.text= "NEXT QUESTION"
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

