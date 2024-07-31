package com.example.banktrack.ui.screens

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.health.connect.datatypes.units.Length
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.os.Bundle
import android.view.View
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
    private var pendingWriteMessage: String? = null
    private lateinit var binding: ReadTagScreenBinding

    var name:String =""
    var number:String =""
    var bank:String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReadTagScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val manager: NfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        nfcAdapter = (manager.defaultAdapter ?: run {
            Toast.makeText(this, "NFC is not supported on this device.", Toast.LENGTH_LONG).show()
            null // Set nfcAdapter to null
        })!!
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        binding.buttonScan.setOnClickListener {
            pendingNfcOperation = { id ->
                name = id.split(";")[1]
                number = id.split(";")[0]
                bank =id.split(";")[2]
                viewModel.getBankDetails(name)

            }
            val intent = Intent(this, ScanningTagScreen::class.java)

            intent.putExtra("number", number)
            intent.putExtra("name", name)
            intent.putExtra("bank", bank)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }

        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        enableForegroundDispatch()
        val filters = arrayOf(IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED))
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, null)
    }

    override fun onPause() {
        super.onPause()
        disableForegroundDispatch()
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
                    Toast.makeText(this, id,Toast.LENGTH_LONG).show()
                    id?.let {
                        val tagItems = it.split(";")
                        if (tagItems.size == 3) {
                            number = tagItems[0]
                            name = tagItems[1]
                            bank = tagItems[2]
                        }

                    }
                    pendingNfcOperation?.invoke(id)
                    pendingNfcOperation = null

                    }
            }
        }
    }

    private fun enableForegroundDispatch() {
        val intentFilter = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        val filters = arrayOf(intentFilter)
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, null)
        showScanningState(true)
    }

    private fun disableForegroundDispatch() {
        nfcAdapter.disableForegroundDispatch(this)
        showScanningState(false)
    }
    private fun showScanningState(isScanning: Boolean) {
        if (isScanning) {
          startActivity(Intent(this, ScanningTagScreen::class.java)) }
    }
}