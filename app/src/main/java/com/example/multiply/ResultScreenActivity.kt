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

        mark_out_of_ten.text = "$finalResult/10"

        when (finalResult) {
            10, 9, 8 -> medal_image.setImageResource(R.drawable.medal_gold)
            7, 6, 5 -> medal_image.setImageResource(R.drawable.medal_silver)
            else -> medal_image.setImageResource(R.drawable.medal_bronze)
        }

        button.setOnClickListener {
            finish()
        }
    }
}