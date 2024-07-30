package com.example.banktrack.ui.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banktrack.databinding.DisplayTagDetailsBinding

class DisplayTagDetails : AppCompatActivity() {

    private lateinit var binding: DisplayTagDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DisplayTagDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }
}