package com.example.banktrack

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banktrack.ui.screens.WriteTagScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var textView = findViewById<TextView>(R.id.writeText)
        var imageView = findViewById<ImageView>(R.id.writeImage)
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