package com.example.banktrack.ui.screens

import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.banktrack.R
import com.example.banktrack.databinding.ScanningTagScreenBinding

class ScanningTagScreen : AppCompatActivity() {

    private lateinit var binding: ScanningTagScreenBinding
    private var nfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScanningTagScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.scanButton.setOnClickListener {
            val intent = Intent(this, DisplayTagDetails::class.java)
            startActivity(intent)
        }

        /*  val nfcManager = getSystemService(NFC_SERVICE) as NfcManager
        nfcAdapter = nfcManager.defaultAdapter

        if (nfcAdapter == null) {
            // NFC is not available on the device
            binding.scanButton.isEnabled = false

        } else {

            // NFC is available, enable the button when a tag is detected
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        intent?.let {
            if (it.action == NfcAdapter.ACTION_NDEF_DISCOVERED || it.action == NfcAdapter.ACTION_TECH_DISCOVERED || it.action == NfcAdapter.ACTION_TAG_DISCOVERED) {

                // A tag has been detected
                handleNfcTag(it)
            }
        }
    }


    private fun handleNfcTag(intent: Intent) {
        binding.scanButton.apply {
            // Change button appearance
            background = resources.getDrawable(R.drawable.button_shape, theme)
            isEnabled = true
        }
    }

        override fun onPause() {
            super.onPause()
            nfcAdapter?.disableForegroundDispatch(this)
        }*/
    }
}
