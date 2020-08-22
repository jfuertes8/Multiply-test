package com.example.multiply

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_start.setOnClickListener {
            if (input_number.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter a table number", Toast.LENGTH_SHORT).show()
            } else {
                var table = input_number.text.toString().toInt()
                val intent = Intent(this, QuestionsActivity::class.java)
                intent.putExtra("table",  table)
                startActivity(intent)
            }
        }
    }
}