package com.example.multiply

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // The next 3 necessary to color status bar same as bg color
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this,R.color.colorBackground)
        setContentView(R.layout.activity_main)

        //Button to start the practice
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

        //Button to the test
        button_test.setOnClickListener {
                val intent = Intent(this, TestActivity::class.java)
                startActivity(intent)
        }
    }
}