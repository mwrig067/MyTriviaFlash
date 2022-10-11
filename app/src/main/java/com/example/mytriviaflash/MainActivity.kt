package com.example.mytriviaflash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.yourpackage.packagenamehere.Flashcard
import com.yourpackage.packagenamehere.FlashcardDatabase

class MainActivity : AppCompatActivity() {

    lateinit var flashcardDatabase: FlashcardDatabase
    var allFlashcard = mutableListOf<Flashcard>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashcardDatabase = FlashcardDatabase(this)
        allFlashcard = flashcardDatabase.getAllCards().toMutableList()


        val flashCardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashCardAnswer = findViewById<TextView>(R.id.flashcard_answer)


        if (allFlashcard.size > 0) {
            flashCardQuestion.text = allFlashcard[0].question
            flashCardAnswer.text = allFlashcard[0].answer

        }

        flashCardQuestion.setOnClickListener {
            flashCardQuestion.visibility = View.INVISIBLE
            flashCardAnswer.visibility = View.VISIBLE
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            {

            }
            val data: Intent? = result.data


            if (data != null) {

                val questionString = data.getStringExtra("QUESTION_KEY")
                val answerString = data.getStringExtra("ANSWER_KEY")

                flashCardQuestion.text = questionString
                flashCardAnswer.text = answerString

                Log.i("Mathew: MainActivity", "question: $questionString")
                Log.i("Mathew: MainActivity", "answer: $questionString")

                if (!questionString.isNullOrEmpty() && !answerString.isNullOrEmpty())
                    flashcardDatabase.insertCard(Flashcard(questionString, answerString))
                allFlashcard = flashcardDatabase.getAllCards().toMutableList()

            }
        }
                val addButton = findViewById<ImageView>(R.id.icon_add)
                    addButton.setOnClickListener {
                val intent = Intent (this,AddCardActivity::class.java)

                resultLauncher.launch(intent)



            }
        }

    }
