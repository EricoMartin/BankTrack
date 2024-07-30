package com.example.banktrack.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banktrack.databinding.ReadTagScreenBinding

class ReadTagScreen : AppCompatActivity() {

    private lateinit var binding: ReadTagScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReadTagScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.buttonScan.setOnClickListener {
            val intent = Intent(this, ScanningTagScreen::class.java)
            startActivity(intent)
        }
    }
}