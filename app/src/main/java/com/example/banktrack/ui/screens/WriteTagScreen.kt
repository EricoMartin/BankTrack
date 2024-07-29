package com.example.banktrack.ui.screens

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.banktrack.MainActivity
import com.example.banktrack.R

class WriteTagScreen : AppCompatActivity() {
//    private val bankList: Array<String> = resources.getStringArray(R.array.banks)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_tag_screen)
        val bankSpinner = findViewById<Spinner>(R.id.bank_name_spinner)
        val writeBtn = findViewById<Button>(R.id.button)
        val backBtn: ImageView = findViewById<android.widget.Toolbar>(R.id.toolbar).findViewById(R.id.backBtn)
        val name  = findViewById<TextView>(R.id.account_name_tv)
        val number = findViewById<TextView>(R.id.account_number_tv)

        val spinAdapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.banks))

        bankSpinner.adapter = spinAdapter
        val spinValue = bankSpinner.selectedItem.toString()
        bankSpinner.setSelection(spinAdapter.getPosition(spinValue))

        backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        if (bankSpinner.isSelected && name.text.isNotEmpty() && number.text.isNotEmpty()){
            writeBtn.setBackgroundColor(resources.getColor(R.color.pry_500))
            writeBtn.setOnClickListener {

            }
        }

    }
}