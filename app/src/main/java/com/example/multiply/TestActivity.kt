package com.example.multiply

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackgroundTest)
        setContentView(R.layout.activity_test)

        test_start_button.setOnClickListener {

            if (table_selected.text.toString().isEmpty() || questions_selected.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter a table and number of questions", Toast.LENGTH_SHORT).show()
            } else if (questions_selected.text.toString().toInt() == 0) {
                Toast.makeText(this, "Please select at least 1 question asked", Toast.LENGTH_SHORT).show()
            } else {
                val table = table_selected.text.toString().toInt()
                val practice: Boolean = false
                val chosenQuestions = questions_selected.text.toString().toInt()

                val intent = Intent(this, QuestionsActivity::class.java)
                intent.putExtra("table", table)
                intent.putExtra("practice", practice)
                intent.putExtra("chosenQuestions", chosenQuestions)

                startActivity(intent)
            }
        }
    }
}