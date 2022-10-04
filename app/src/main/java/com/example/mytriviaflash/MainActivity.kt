package com.example.mytriviaflash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flashCardQuestion = findViewById<TextView>(R.id.flashcard_question)
        val flashCardAnswer = findViewById<TextView>(R.id.flashcard_answer)


        flashCardQuestion.setOnClickListener {
            flashCardQuestion.visibility = View.INVISIBLE
            flashCardAnswer.visibility = View.VISIBLE
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result-> {

        }
            val data: Intent? = result.data


               if (data != null) {

                   val questionString = data.getStringExtra("QUESTION_KEY")
                   val answerString = data.getStringExtra("ANSWER_KEY")

                   flashCardQuestion.text = questionString
                   flashCardAnswer.text = answerString
               }


        }
                val addButton = findViewById<ImageView>(R.id.icon_add)
                    addButton.setOnClickListener {
                val intent = Intent (this,AddCardActivity::class.java)

                resultLauncher.launch(intent)
            }
        }




}