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
        val view = binding.root
        setContentView(view)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        binding.bankNameValue.text = intent.getStringExtra("bank")!!.toString()
        binding.accountNumberTv.text = intent.getStringExtra("number")!!.toString()
        binding.accountNameTv.text = intent.getStringExtra("name")!!.toString()

        val name =  intent.getStringExtra("name")!!.toString()
        binding.button.setOnClickListener{
            Toast.makeText(this, "Name is $name", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}