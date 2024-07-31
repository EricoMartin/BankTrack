package com.example.banktrack.ui.screens

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.banktrack.R
import com.example.banktrack.databinding.ScanningTagScreenBinding
import com.example.banktrack.ui.viewmodel.MainViewModel
import com.example.banktrack.utils.NFCHelper

class ScanningTagScreen : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pendingIntent: PendingIntent
    private var pendingNfcOperation: ((String) -> Unit)? = null
    private var pendingWriteMessage: String? = null
    private lateinit var binding: ScanningTagScreenBinding

    var acctName: String = ""
    var acctNumber: String = ""
    var bankName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ScanningTagScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.scanButton.setOnClickListener {
            pendingNfcOperation = { id ->
                viewModel.getBankDetails(id.toInt())
            }
            val intent = Intent(this, DisplayTagDetails::class.java)
            intent.putExtra("name", acctName)
            intent.putExtra("number", acctNumber)
            intent.putExtra("bank", bankName)
            startActivity(intent)

        }
        viewModel.bankDetails.observe(this) { details ->
            acctName = details.accountName
            acctNumber = details.accountNumber
            bankName = details.bankName
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }

        val manager: NfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        nfcAdapter = (manager.defaultAdapter ?: run {
            Toast.makeText(this, "NFC is not supported on this device.", Toast.LENGTH_LONG).show()
            null // Set nfcAdapter to null
        })!!

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

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent?.let {
            if (NfcAdapter.ACTION_TAG_DISCOVERED == it.action ||
                NfcAdapter.ACTION_TECH_DISCOVERED== it.action ||
                NfcAdapter.ACTION_TAG_DISCOVERED == it.action) {
                val tag = it.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
                tag?.let {
                        val id = NFCHelper.readFromTag(tag)
                        pendingNfcOperation?.invoke(id)
                        pendingNfcOperation = null
                    }
                }
            }
        }
}
