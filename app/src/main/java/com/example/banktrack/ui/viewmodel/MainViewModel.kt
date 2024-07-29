package com.example.banktrack.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.banktrack.data.database.BankTrackDB
import com.example.banktrack.data.models.BankDetail
import com.example.banktrack.repository.MainRepository

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MainRepository
    private val _bankDetails = MutableLiveData<BankDetail>()
    val bankDetails: LiveData<BankDetail> get() = _bankDetails
    val error = MutableLiveData<String>()

    init {
        val detailsDao = BankTrackDB.getDatabase(application).detailsDao()
        repository = MainRepository(detailsDao)
    }
}