package com.example.banktrack.repository

import com.example.banktrack.data.dao.BankDetailDao
import com.example.banktrack.data.models.BankDetail
import kotlinx.coroutines.flow.Flow

class MainRepository (private val detailDao: BankDetailDao ) {
    val allDetails: Flow<List<BankDetail>> = detailDao.getAllBankDetails()

    suspend fun insertDetail(bankDetail: BankDetail) {
        detailDao.insertBankDetail(bankDetail)
    }
    suspend fun getDetail(acctName: String): BankDetail {
        return detailDao.getBankDetail(acctName)
    }
    suspend fun deleteAll() {
        detailDao.deleteAll()
    }
}