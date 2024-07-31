package com.example.banktrack.ui.screens

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.NfcManager
import android.nfc.Tag
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.banktrack.MainActivity
import com.example.banktrack.R
import com.example.banktrack.data.models.BankDetail
import com.example.banktrack.ui.viewmodel.MainViewModel
import com.example.banktrack.utils.NFCHelper
import com.google.android.material.button.MaterialButton

class WriteNFCScreen : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var nfcAdapter: NfcAdapter
    private lateinit var pendingIntent: PendingIntent
    private var pendingNfcOperation: ((String) -> Unit)? = null
    private var pendingWriteMessage: String? = null
    private lateinit var save: Button
    private lateinit var backBtn: ImageView
    private lateinit var successBtn: Button
    private lateinit var info: TextView
    private lateinit var desc: TextView
    private lateinit var nfcImg: ImageView
    private lateinit var nfcNotFound: TextView
    private lateinit var nfcSearch: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.write_nfc_screen)


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        save = findViewById<Button>(R.id.button)
        backBtn = findViewById<ImageView>(R.id.backBtn)
        info = findViewById<TextView>(R.id.place_tag_c)
        desc = findViewById<TextView>(R.id.place_the_n)
        nfcImg = findViewById<ImageView>(R.id.nfc_img)
        nfcNotFound = findViewById<TextView>(R.id.nfc_not_found)
        nfcSearch = findViewById(R.id.nfc_search)
        successBtn = findViewById(R.id.button1)

        val manager: NfcManager = getSystemService(Context.NFC_SERVICE) as NfcManager
        nfcAdapter = (manager.defaultAdapter ?: run {
            // Handle NFC not supported case
            Toast.makeText(this, "NFC is not supported on this device.", Toast.LENGTH_LONG).show()
            info.text = "Tag not found"
            desc.text = "Your device is not NFC compatible"
            nfcImg.visibility = View.GONE
            nfcNotFound.visibility = View.VISIBLE
            save.visibility = View.GONE
            null // Set nfcAdapter to null
        })!!

        successBtn.setOnClickListener {
            val bankDetails = BankDetail(
                accountNumber = intent.getStringExtra("number")!!,
                accountName = intent.getStringExtra("name")!!,
                bankName = intent.getStringExtra("bank")!!
            )
            viewModel.insert(bankDetails)
            pendingWriteMessage =
                "${bankDetails.accountNumber};${bankDetails.accountName};${bankDetails.bankName}"
        }

        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        backBtn.setOnClickListener {
            startActivity(Intent(this, WriteTagScreen::class.java))
        }

        viewModel.bankDetails.observe(this) { details ->
            Toast.makeText(
                this, "Account: ${details.accountNumber}, " +
                        "Name: ${details.accountName}, " +
                        "Bank: ${details.bankName}", Toast.LENGTH_LONG
            ).show()
        }

        viewModel.error.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()

        enableForegroundDispatch()
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        disableForegroundDispatch()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.let {
            if (NfcAdapter.ACTION_TAG_DISCOVERED == it.action ||
                NfcAdapter.ACTION_TECH_DISCOVERED == it.action ||
                NfcAdapter.ACTION_TAG_DISCOVERED == it.action
            ) {
                val tag = it.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
                tag?.let {
                    if (pendingWriteMessage != null) {
                        val success = NFCHelper.writeToTag(tag, pendingWriteMessage!!)
                        if (success) {
                            nfcImg.visibility = View.GONE
                            nfcSearch.visibility = View.VISIBLE
                            info.text = "Upload successful"
                            desc.text =
                                "Your information has been successfully uploaded to the NFC tag."
                            Toast.makeText(this, "Written to NFC tag", Toast.LENGTH_SHORT).show()
                            save.visibility = View.GONE
                            successBtn.visibility = View.VISIBLE
                        } else {
                            Toast.makeText(this, "Failed to write to NFC tag", Toast.LENGTH_SHORT)
                                .show()
                        }
                        pendingWriteMessage = null
                    } else {
                        val id = NFCHelper.readFromTag(tag)
                        pendingNfcOperation?.invoke(id)
                        pendingNfcOperation = null
                        showScanningState(true)
                    }
                }
                showScanningState(false)
            }
        }
    }

    private fun showScanningState(isScanning: Boolean) {
        if (isScanning) {
            nfcImg.visibility = View.GONE
            nfcSearch.visibility = View.VISIBLE
            info.setTextColor(resources.getColor(R.color.pry_500))
            info.text = "Scanning ..."
            desc.text =
                "BankTrack uses AES-256 encryption and is trusted by millions of user rounds the globe."
        } else {
            nfcImg.visibility = View.VISIBLE
            nfcSearch.visibility = View.GONE
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
}