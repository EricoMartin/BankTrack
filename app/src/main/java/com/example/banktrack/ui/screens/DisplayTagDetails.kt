package com.example.banktrack.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.banktrack.MainActivity
import com.example.banktrack.databinding.DisplayTagDetailsBinding

class DisplayTagDetails : AppCompatActivity() {

    private lateinit var binding: DisplayTagDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DisplayTagDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.bankNameValue.text = intent.getStringExtra("bank")
        binding.accountNumberTv.text = intent.getStringExtra("number")
        binding.accountNameTv.text = intent.getStringExtra("name")

        binding.button.setOnClickListener {
            val name = intent.getStringExtra("name")
            Toast.makeText(this, "Name is $name", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
