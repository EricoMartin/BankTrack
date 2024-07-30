package com.example.banktrack.ui.screens

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.banktrack.MainActivity
import com.example.banktrack.R

class WriteTagScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.write_tag_screen)

        val bankSpinner = findViewById<Spinner>(R.id.bank_name_spinner)
        val writeBtn = findViewById<Button>(R.id.button)
        val backBtn: ImageView = findViewById<android.widget.Toolbar>(R.id.toolbar).findViewById(R.id.backBtn)
        val name = findViewById<EditText>(R.id.account_name_tv)
        val number = findViewById<EditText>(R.id.account_number_tv)

        val spinAdapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.banks))

        bankSpinner.adapter = spinAdapter

        // Set the initial state for the Spinner background
        bankSpinner.background = ContextCompat.getDrawable(this, R.drawable.unfocused_text_field_bkg)

        bankSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Change the background when an item is selected
                bankSpinner.background = ContextCompat.getDrawable(this@WriteTagScreen, R.drawable.focused_text_field_bkg)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Change the background when nothing is selected
                bankSpinner.background = ContextCompat.getDrawable(this@WriteTagScreen, R.drawable.unfocused_text_field_bkg)
            }
        }

        // Set up TextWatchers for the EditText fields
        setupTextWatchers(writeBtn, name, number, bankSpinner)

        // Set up OnFocusChangeListener for Spinner
        bankSpinner.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            bankSpinner.background = if (hasFocus) {
                ContextCompat.getDrawable(this, R.drawable.focused_text_field_bkg)
            } else {
                ContextCompat.getDrawable(this, R.drawable.unfocused_text_field_bkg)
            }
        }

        // Make the Spinner request focus initially so the OnFocusChangeListener is activated
        bankSpinner.requestFocus()
        bankSpinner.clearFocus()

        backBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun setupTextWatchers(writeBtn: Button, name: EditText, number: EditText, bankSpinner: Spinner) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateWriteButtonState(writeBtn, name, number, bankSpinner)
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        name.addTextChangedListener(textWatcher)
        number.addTextChangedListener(textWatcher)
        // Initialize button state based on initial text
        updateWriteButtonState(writeBtn, name, number, bankSpinner)
    }

    private fun updateWriteButtonState(writeBtn: Button, name: EditText, number: EditText, bankSpinner: Spinner) {
        val areFieldsValid = name.text.isNotEmpty() && number.text.isNotEmpty() && bankSpinner.selectedItem != null
        writeBtn.isEnabled = areFieldsValid
        writeBtn.setBackgroundColor(if (areFieldsValid) ContextCompat.getColor(this, R.color.orange) else ContextCompat.getColor(this, R.color.gray))
    }
}