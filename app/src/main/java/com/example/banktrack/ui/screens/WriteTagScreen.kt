package com.example.banktrack.ui.screens

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.banktrack.MainActivity
import com.example.banktrack.R
import com.example.banktrack.databinding.WriteTagScreenBinding
import com.example.banktrack.ui.viewmodel.MainViewModel
import com.example.banktrack.utils.NFCHelper

class WriteTagScreen : AppCompatActivity() {

    private lateinit var binding: WriteTagScreenBinding

    private lateinit var viewModel: MainViewModel
    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pendingIntent: PendingIntent
    private var nfcTag: Tag? = null

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

        val manager: NfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        nfcAdapter = (manager.defaultAdapter ?: run {
            // Handle NFC not supported case
            Toast.makeText(this, "NFC is not supported on this device.", Toast.LENGTH_LONG).show()
            null // Set nfcAdapter to null
        })!!
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
        //Collect user bank details
        val bankDetails = "${number.text.toString()}|${name.text.toString()}|${bankSpinner.selectedItem.toString()}"

//            writeBtn.setBackgroundColor(resources.getColor(R.color.pry_500))
        writeBtn.setOnClickListener {
            if (nfcTag != null && NFCHelper.writeToTag(nfcTag!!, bankDetails)) {
                Toast.makeText(this, "Bank details saved to NFC tag", Toast.LENGTH_SHORT).show()
                intent.putExtra("name", name.text.toString())
                intent.putExtra("number", number.text.toString())
                intent.putExtra("bank", bankSpinner.selectedItem.toString())
                val intent = Intent(this, WriteNFCScreen::class.java)
                startActivity(intent)
                nfcTag = null // Clear the tag after writing
            } else {
                Toast.makeText(this, "Failed to write to NFC tag", Toast.LENGTH_SHORT).show()
            }

        }


        bankSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                isSpinnerSelected = position != 0 // First item is "Select an item"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                isSpinnerSelected = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val filters = arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED))
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.let {
            if (NfcAdapter.ACTION_TAG_DISCOVERED == it.action ||
                NfcAdapter.ACTION_TECH_DISCOVERED== it.action ||
                NfcAdapter.ACTION_TAG_DISCOVERED == it.action) {
                nfcTag = it.getParcelableExtra(NfcAdapter.EXTRA_TAG)
                Toast.makeText(this, "NFC tag detected", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to write to NFC tag", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


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
}