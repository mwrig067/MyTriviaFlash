package com.example.mytriviaflash

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {

    lateinit var flashcardDatabase: FlashcardDatabase
    var allFlashcard = mutableListOf<Flashcard>()

    var currCardDisplayedIndex = 0

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
// get the center for the clipping circle

// get the center for the clipping circle
            val cx = flashCardAnswer.width / 2
            val cy = flashCardAnswer.height / 2

// get the final radius for the clipping circle

// get the final radius for the clipping circle
            val finalRadius = Math.hypot(cx.toDouble(), cy.toDouble()).toFloat()

// create the animator for this view (the start radius is zero)

// create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(flashCardAnswer, cx, cy, 0f, finalRadius)

// hide the question and show the answer to prepare for playing the animation!

// hide the question and show the answer to prepare for playing the animation!
            flashCardQuestion.visibility = View.INVISIBLE
            flashCardAnswer.visibility = View.VISIBLE

            anim.duration = 3000
            anim.start()
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
                overridePendingTransition(R.anim.right_in, R.anim.left_out)
            }

        val nextButton = findViewById<ImageView>(R.id.flashcard_next_button)
        nextButton.setOnClickListener {
            // not empty
           if (allFlashcard.isEmpty()){
               return@setOnClickListener
           }
            // (1) load the resource animation file. (it.context) did not work so I inputted this
            val leftOutAnim = AnimationUtils.loadAnimation(this, R.anim.left_out)
            val rightInAnim = AnimationUtils.loadAnimation(this, R.anim.right_in)
            // (2) play the two animations in sequence by setting listeners to know when the animation is finished.

            leftOutAnim.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    flashCardQuestion.visibility = View.VISIBLE
                    flashCardAnswer.visibility = View.INVISIBLE
                }

                override fun onAnimationEnd(animation: Animation?) {
                    // this method is called when the animation is finished playing
                    flashCardQuestion.startAnimation(rightInAnim)

                    currCardDisplayedIndex++

                    // makes sure counter is not out of bounds
                    if (currCardDisplayedIndex >= allFlashcard.size) {
                        currCardDisplayedIndex = 0

                    }

                    allFlashcard = flashcardDatabase.getAllCards().toMutableList()

                    val question = allFlashcard[currCardDisplayedIndex].question
                    val answer = allFlashcard[currCardDisplayedIndex].answer


                    flashCardQuestion.text = question
                    flashCardAnswer.text = answer

                    flashCardQuestion.visibility = View.VISIBLE
                    flashCardAnswer.visibility = View.INVISIBLE

                    flashCardQuestion.startAnimation(rightInAnim)
                }

                override fun onAnimationRepeat(animation: Animation?) {
                    // we don't need to worry about this method
                }
            })
            flashCardQuestion.startAnimation(leftOutAnim)
        }





        }

    }



