package com.example.banktrack

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.banktrack.ui.screens.ReadTagScreen
import com.example.banktrack.ui.screens.WriteTagScreen

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val readCard = findViewById<CardView>(R.id.read_card)
        val writeCard = findViewById<CardView>(R.id.write_card)

        moveToWriteScreen(writeCard)
        moveToReadScreen(readCard)
    }

    private fun moveToWriteScreen(writeCard: CardView) {
        writeCard.setOnClickListener {
            val write = Intent(this, WriteTagScreen::class.java)
            startActivity(write)
        }
    }

    private fun moveToReadScreen(readCard: CardView) {
        readCard.setOnClickListener {
            val read = Intent(this, ReadTagScreen::class.java)
            startActivity(read)
        }
    }


}