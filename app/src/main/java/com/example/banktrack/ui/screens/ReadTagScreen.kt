package com.example.banktrack.ui.screens

import android.app.PendingIntent
import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.banktrack.databinding.ReadTagScreenBinding
import com.example.banktrack.ui.viewmodel.MainViewModel

class ReadTagScreen : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pendingIntent: PendingIntent
    private var pendingNfcOperation: ((String) -> Unit)? = null
    private var pendingWriteMessage: String? = null
    private lateinit var binding: ReadTagScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReadTagScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.buttonScan.setOnClickListener {
            pendingNfcOperation = { id ->
                viewModel.getBankDetails(id.toInt())
            }
            val intent = Intent(this, ScanningTagScreen::class.java)
            startActivity(intent)
        }
    }
}