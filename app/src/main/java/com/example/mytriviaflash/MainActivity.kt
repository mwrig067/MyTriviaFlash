package com.example.mytriviaflash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    lateinit var flashcardDatabase: FlashcardDatabase
    var allFlashcard = mutableListOf<Flashcard>()

    var currCardDisplayedIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashcardDatabase = FlashcardDatabase(this)
        allFlashcard = flashcardDatabase.getAllCards().toMutableList()


        val flashCardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashCardAnswer = findViewById<TextView>(R.id.flashcard_answer)
        val flashCardDelete = findViewById<ImageView>(R.id.flashcard_delete_icon)



        flashCardDelete.setOnClickListener {
            val flashcardQuestionToDelete = flashCardQuestion.text.toString()
            flashcardDatabase.deleteCard(flashcardQuestionToDelete)
            Log.i("Main Activity","press delete")
        }


        if (allFlashcard.size > 0) {
            flashCardQuestion.text = allFlashcard[0].question
            flashCardAnswer.text = allFlashcard[0].answer

        }

        flashCardQuestion.setOnClickListener {
            flashCardQuestion.visibility = View.INVISIBLE
            flashCardAnswer.visibility = View.VISIBLE
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

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

        val nextButton = findViewById<ImageView>(R.id.flashcard_next_button)
        nextButton.setOnClickListener {
            // not empty
           if (allFlashcard.isEmpty()){

               return@setOnClickListener
           }
            // increment counter
            currCardDisplayedIndex++

            // makes sure counter is not out of bounds
            if (currCardDisplayedIndex >= allFlashcard.size) {
                currCardDisplayedIndex = 0

            }

            allFlashcard = flashcardDatabase.getAllCards().toMutableList()
              //  [{q,a} ,  {q1,a1} , {q2,a2}]
            //       0          1         2        3
            val question = allFlashcard[currCardDisplayedIndex].question
            val answer = allFlashcard[currCardDisplayedIndex].answer


            flashCardQuestion.text = question
            flashCardAnswer.text = answer

            flashCardQuestion.visibility = View.VISIBLE
            flashCardAnswer.visibility = View.INVISIBLE


        }

        }

    }



