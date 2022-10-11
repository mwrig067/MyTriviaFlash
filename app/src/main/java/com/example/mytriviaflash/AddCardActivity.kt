package com.example.mytriviaflash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.yourpackage.packagenamehere.Flashcard
import com.yourpackage.packagenamehere.FlashcardDatabase

class AddCardActivity : AppCompatActivity() {


    lateinit var flashcardDatabase: FlashcardDatabase
    var allFlashcard = mutableListOf<Flashcard>()
    var currCardDisplayedIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        flashcardDatabase = FlashcardDatabase(this)
        var allFlashcards = flashcardDatabase.getAllCards().toMutableList()


        val saveButton = findViewById<ImageView>(R.id.flashcard_save_button)
        saveButton.setOnClickListener {


            val flashcardQuestion = findViewById<TextView>(R.id.flashcard_question)
            val flashcardAnswer = findViewById<TextView>(R.id.flashcard_answer)
            val questionEditText = findViewById<EditText>(R.id.flashcard_question_edittext)
            val answerEditText = findViewById<EditText>(R.id.flashcard_answer_edittext)
            val questionString = questionEditText.text.toString()
            val answerString = answerEditText.text.toString()

                if (allFlashcards.size > 0) {
                flashcardQuestion.text = allFlashcards[0].question
                flashcardAnswer.text = allFlashcards[0].answer
            }

            flashcardQuestion.setOnClickListener {
              flashcardAnswer.visibility = View.INVISIBLE
              flashcardQuestion.visibility = View.VISIBLE
            }



            val data = Intent()
            data.putExtra("QUESTION_KEY", questionString)
            data.putExtra("ANSWER_KEY", answerString)

            setResult(RESULT_OK, data)
            finish()
        }


        val cancelButton = findViewById<ImageView>(R.id.flashcard_cancel_button)
        cancelButton.setOnClickListener{
            finish()
        }

    }




}

