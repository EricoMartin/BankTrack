package com.example.banktrack.ui.screens

import android.content.Intent
import android.graphics.Color
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
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.banktrack.MainActivity
import com.example.banktrack.R
import com.example.banktrack.databinding.WriteTagScreenBinding

class WriteTagScreen : AppCompatActivity() {

    private lateinit var binding: WriteTagScreenBinding

    private lateinit var writeBtn: Button
    private lateinit var bankSpinner: Spinner
    private lateinit var backBtn: ImageView

    private var isSpinnerSelected: Boolean = false
    private var isTextView1Filled: Boolean = false
    private var isTextView2Filled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Instantiate binding
        binding = WriteTagScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Instantiate views
        bankSpinner = findViewById<Spinner>(R.id.bank_name_spinner)
        writeBtn = findViewById<Button>(R.id.button)
        val backBtn: ImageView =
            findViewById<android.widget.Toolbar>(R.id.toolbar).findViewById(R.id.backBtn)
        val name = findViewById<EditText>(R.id.account_name_tv)
        val number = findViewById<EditText>(R.id.account_number_tv)
        // Adapter for spinner
        val spinAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.banks)
        )
        // Set spinner adapter
        bankSpinner.adapter = spinAdapter
        // Get current spinner value
        var spinValue = bankSpinner.selectedItem.toString()
        // Sets the current value to be display in the spinner
        bankSpinner.setSelection(spinAdapter.getPosition(spinValue))

        // Set the initial state for the Spinner background
        bankSpinner.background =
            ContextCompat.getDrawable(this, R.drawable.unfocused_text_field_bkg)

        bankSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Change the background when an item is selected
                bankSpinner.background = ContextCompat.getDrawable(
                    this@WriteTagScreen,
                    R.drawable.focused_text_field_bkg
                )
                spinValue = bankSpinner.selectedItem.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Change the background when nothing is selected
                bankSpinner.background = ContextCompat.getDrawable(
                    this@WriteTagScreen,
                    R.drawable.unfocused_text_field_bkg
                )
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


//            writeBtn.setBackgroundColor(resources.getColor(R.color.pry_500))
        writeBtn.setOnClickListener {
            val intent = Intent(this, WriteNFCScreen::class.java)
            intent.putExtra("name", name.text.toString())
            intent.putExtra("number", number.text.toString())
//                intent.putExtra("bank", spinValue)
            intent.putExtra("bank", bankSpinner.selectedItem.toString())
            startActivity(intent)
        }


        bankSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                isSpinnerSelected = position != 0 // First item is "Select an item"
//                updateButtonState()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                isSpinnerSelected = false
//                updateButtonState()
            }
        }
    }
//
//    private fun updateButtonState() {
//        if (isSpinnerSelected && isTextView1Filled && isTextView2Filled) {
//            writeBtn.isEnabled = true
//            writeBtn.setBackgroundColor(resources.getColor(R.color.pry_500)) // Change to your desired color
//        } else {
//            writeBtn.isEnabled = false
//            writeBtn.setBackgroundColor(Color.GRAY) // Change to your disabled color
//        }
//    }


    private fun setupTextWatchers(
        writeBtn: Button,
        name: EditText,
        number: EditText,
        bankSpinner: Spinner
    ) {
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

    private fun updateWriteButtonState(
        writeBtn: Button,
        name: EditText,
        number: EditText,
        bankSpinner: Spinner
    ) {
        val areFieldsValid =
            name.text.isNotEmpty() && number.text.isNotEmpty() && bankSpinner.selectedItem != null
        writeBtn.isEnabled = areFieldsValid
        writeBtn.setBackgroundResource(
            if (areFieldsValid) R.drawable.button_shape else R.drawable.disabled_btn_bkg
        )
    }


//    private fun updateWriteButtonState(
//        writeBtn: Button,
//        name: EditText,
//        number: EditText,
//        bankSpinner: Spinner
//    ) {
//        val areFieldsValid =
//            name.text.isNotEmpty() && number.text.isNotEmpty() && bankSpinner.selectedItem != null
//        writeBtn.isEnabled = areFieldsValid
//        writeBtn.setBackgroundColor(
//            if (areFieldsValid) ContextCompat.getColor(
//                this,
//                R.color.pry_500
//            ) else ContextCompat.getColor(this, R.color.gray)
//        )
//    }
//}

//private fun setUpSpinner() {

}