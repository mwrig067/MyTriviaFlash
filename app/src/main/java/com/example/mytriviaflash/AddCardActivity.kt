package com.example.mytriviaflash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        val cancelButton = findViewById<ImageView>(R.id.icon_cancel_button)

        cancelButton.setOnClickListener{
            val intent = Intent ( this,MainActivity::class.java)
            startActivity(intent)


        }



    }



}

