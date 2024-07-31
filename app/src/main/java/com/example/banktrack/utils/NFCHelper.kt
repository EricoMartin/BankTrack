package com.example.banktrack.utils

import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef


object NFCHelper {
    fun readFromTag(tag: Tag): String {
        var string = ""
        if (tag == null) return string
        val ndef = Ndef.get(tag) ?: return string
        return try {
            ndef.connect()
            val ndefMessage = ndef.ndefMessage ?: return string
            val record = ndefMessage.records.firstOrNull() ?: return string
            val payload = record.payload
            ndef.close()
            String(payload, Charsets.UTF_8)
        } catch (e: Exception) {
            // Handle error (e.g., NFC read failure)
            return ""
        }
    }


//            String {
//        val ndef = Ndef.get(tag)
//        ndef?.connect()
//        val ndefMessage = ndef?.ndefMessage
//        val message = ndefMessage?.records?.firstOrNull()?.payload?.decodeToString()
//        ndef?.close()
//        return message ?: "Error reading NFC tag"


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