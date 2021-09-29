package com.example.a2in1app

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

lateinit var numbersConstraintLayout: ConstraintLayout
lateinit var numbersGuessList: ArrayList<String>
lateinit var userGuess: EditText
lateinit var numbersRecyclerView: RecyclerView
lateinit var btn: Button
var numberOfGuesses = 3
val random = Random.nextInt(11)

class NumbersGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_numbers_game)

        numbersGuessList = arrayListOf()

        numbersConstraintLayout = findViewById(R.id.cl)
        userGuess = findViewById(R.id.etNumGuessed)
        btn = findViewById(R.id.guessBtn)
        btn.setOnClickListener{
            check()
            updatenumbersRecyclerView()
        }
    }

    private fun check(){

        if (userGuess.text.isEmpty()){
            Snackbar.make(numbersConstraintLayout, "You must enter a number", Snackbar.LENGTH_SHORT).show()
            return
        }

        val guessToInt = userGuess.text.toString().toInt()
        if (random == guessToInt){
            numbersGuessList.add("That's right! it is $random")
            customAlert()
        }
        else {
            var plural = "guesses"
            if (--numberOfGuesses == 1) plural = "guess"
            numbersGuessList.add("You guessed $guessToInt\nYou have $numberOfGuesses $plural left")
        }
        if (numberOfGuesses == 0) {
            numbersGuessList.add("The number was $random")
            customAlert()
        }
        userGuess.setText("")
    }

    private fun updatenumbersRecyclerView(){
        numbersRecyclerView = findViewById(R.id.rvGuesses)
        numbersRecyclerView.adapter = RecyclerViewAdapter(numbersGuessList)
        numbersRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun customAlert(){
        // first we create a variable to hold an AlertDialog builder
        val dialogBuilder = AlertDialog.Builder(this)
        // then we set up the input
        val input = EditText(this)
        // here we set the message of our alert dialog
        dialogBuilder.setMessage("Want to play again?")
            // positive button text and action
            .setPositiveButton("Sure!", DialogInterface.OnClickListener {
                    _, _ -> recreate()
                numberOfGuesses = 3
            })
            // negative button text and action
            .setNegativeButton("Nope", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
                userGuess.isEnabled = false
            })
        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle("Game ended!")
        // show alert dialog
        alert.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu!!.getItem(0).title = "New Game"
        menu!!.getItem(1).title = "Guess The Phrase"
        menu!!.getItem(2).isVisible = true
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_0 -> {
                recreate()
                numberOfGuesses = 3
                return true
            }
            R.id.item_1 -> {
                val intent = Intent(this, GuessThePhraseActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.item_2 -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}