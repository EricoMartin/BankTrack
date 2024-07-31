package com.example.banktrack.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.banktrack.data.database.BankTrackDB
import com.example.banktrack.data.models.BankDetail
import com.example.banktrack.repository.MainRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MainRepository
    private val _bankDetails = MutableLiveData<BankDetail>()
    val bankDetails: LiveData<BankDetail> get() = _bankDetails
    val error = MutableLiveData<String>()

    init {
        val detailsDao = BankTrackDB.getDatabase(application).detailsDao()
        repository = MainRepository(detailsDao)
    }

    fun insert(bankDetails: BankDetail) = viewModelScope.launch {
        try {
            repository.insertDetail(bankDetails)
        } catch (e: Exception) {
            error.value = "Error inserting bank details: ${e.message}"
        }
    }

    fun getBankDetails(name: String) = viewModelScope.launch {
        try {
            val details = repository.getDetail(name.toString())
            _bankDetails.value = details
        } catch (e: Exception) {
            error.value = "Error fetching bank details: ${e.message}"
        }
    }
}