package com.example.banktrack.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank_details")
data class BankDetail(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val accountName: String,
    val accountNumber: String,
    val bankName: String,
)
