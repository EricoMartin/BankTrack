package com.example.banktrack

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.banktrack.ui.screens.WriteTagScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.writeText)
        val imageView = findViewById<ImageView>(R.id.writeImage)
        moveToWriteScreen(textView, imageView)
    }

    private fun moveToWriteScreen(textView: TextView?, imageView: ImageView?) {
        textView!!.setOnClickListener {
            Intent(this, WriteTagScreen::class.java)
        }
        imageView!!.setOnClickListener {
            Intent(this, WriteTagScreen::class.java)
        }
    }


}