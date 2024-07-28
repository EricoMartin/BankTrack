package com.example.banktrack.utils

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef


object NFCHelper {
    fun readFromTag(tag: Tag): String {
        val ndef = Ndef.get(tag)
        ndef?.connect()
        val ndefMessage = ndef?.ndefMessage
        val message = ndefMessage?.records?.firstOrNull()?.payload?.decodeToString()
        ndef?.close()
        return message ?: "Error reading NFC tag"
    }

    fun writeToTag(tag: Tag, message: String): Boolean {
        val ndefMessage = NdefMessage(arrayOf(NdefRecord.createTextRecord(null, message)))
        val ndef = Ndef.get(tag)
        return try {
            ndef?.connect()
            ndef?.writeNdefMessage(ndefMessage)
            ndef?.close()
            true
        } catch (e: Exception) {
            false
        }
    }
}