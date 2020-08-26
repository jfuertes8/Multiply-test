package com.example.multiply

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result_screen.*

class ResultScreenActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_screen)

        //Get data from intent
        val intent = intent
        val finalResult: Int = intent.getIntExtra("correctAnswers", 0)
        val totalQuestions: Int = intent.getIntExtra("totalQuestions", 0)

        mark_out_of_ten.text = "$finalResult / $totalQuestions"

        if (finalResult == totalQuestions) {
            medal_image.setImageResource(R.drawable.medal_gold)
        } else if (finalResult <= totalQuestions/2) {
            medal_image.setImageResource(R.drawable.medal_bronze)
        } else {
            medal_image.setImageResource(R.drawable.medal_silver)
        }

        button.setOnClickListener {
            finish()
        }
    }
}