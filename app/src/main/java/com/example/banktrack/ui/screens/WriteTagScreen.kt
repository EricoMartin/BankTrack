package com.example.banktrack.ui.screens

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
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
    private lateinit var writeBtn: Button
    private lateinit var bankSpinner: Spinner
    private lateinit var backBtn: ImageView

    private var isSpinnerSelected: Boolean = false
    private var isTextView1Filled: Boolean = false
    private var isTextView2Filled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_tag_screen)
        bankSpinner = findViewById<Spinner>(R.id.bank_name_spinner)
        writeBtn = findViewById<Button>(R.id.button)
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


            writeBtn.setBackgroundColor(resources.getColor(R.color.pry_500))
            writeBtn.setOnClickListener {
                val intent = Intent(this, WriteNFCScreen::class.java)
                intent.putExtra("name", name.text.toString())
                intent.putExtra("number", number.text.toString())
                intent.putExtra("bank", spinValue.toString())
                startActivity(intent)
            }


        bankSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                isSpinnerSelected = position != 0 // First item is "Select an item"
//                updateButtonState()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                isSpinnerSelected = false
//                updateButtonState()
            }
        }
    }

    private fun updateButtonState() {
        if (isSpinnerSelected && isTextView1Filled && isTextView2Filled) {
            writeBtn.isEnabled = true
            writeBtn.setBackgroundColor(resources.getColor(R.color.pry_500)) // Change to your desired color
        } else {
            writeBtn.isEnabled = false
            writeBtn.setBackgroundColor(Color.GRAY) // Change to your disabled color
        }
    }

}