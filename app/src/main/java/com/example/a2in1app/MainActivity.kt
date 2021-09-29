package com.example.a2in1app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

lateinit var numbers: Button
lateinit var phrase: Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numbers = findViewById(R.id.numbersBtn)
        numbers.setOnClickListener {
            val intent = Intent(this, NumbersGameActivity::class.java)
            startActivity(intent)
        }

        phrase = findViewById(R.id.phraseBtn)
        phrase.setOnClickListener {
            val intent = Intent(this, GuessThePhraseActivity::class.java)
            startActivity(intent)
        }
    }
}