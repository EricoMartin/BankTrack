package com.example.banktrack.ui.screens

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.banktrack.R
import com.example.banktrack.databinding.ReadTagScreenBinding
import com.example.banktrack.ui.viewmodel.MainViewModel
import com.example.banktrack.utils.NFCHelper

class ReadTagScreen : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pendingIntent: PendingIntent
    private var pendingNfcOperation: ((String) -> Unit)? = null
    private lateinit var binding: ReadTagScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReadTagScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val manager: NfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        nfcAdapter = manager.defaultAdapter ?: run {
            Toast.makeText(this, "NFC is not supported on this device.", Toast.LENGTH_LONG).show()
            null // Set nfcAdapter to null
        }!!

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.buttonScan.setOnClickListener {
            pendingNfcOperation = { id ->
                // Toast For debugging, remove when done
                Toast.makeText(this,id, Toast.LENGTH_LONG).show()
                val tagItems = id.split(";")
                if (tagItems.size == 3) {
                    val number = tagItems[0]
                    val name = tagItems[1]
                    val bank = tagItems[2]

                    val intent = Intent(this, DisplayTagDetails::class.java).apply {
                        putExtra("number", number)
                        putExtra("name", name)
                        putExtra("bank", bank)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val filters = arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED))
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, null)
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action ||
            NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            tag?.let {
                val id = NFCHelper.readFromTag(tag)
                id?.let {
                    pendingNfcOperation?.invoke(it)
                    pendingNfcOperation = null
                }
            }
        }
    }
}
