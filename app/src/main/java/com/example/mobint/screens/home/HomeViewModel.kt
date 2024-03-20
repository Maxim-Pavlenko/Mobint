package com.example.mobint.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobint.data.repository.CompanyRepository
import com.example.mobint.entities.CompanyResponse
import kotlinx.coroutines.launch

class HomeViewModel (
    private val companyRepository: CompanyRepository
): ViewModel() {
    private val _cardData = MutableLiveData<CompanyResponse?>()
    val cardData: LiveData<CompanyResponse?> = _cardData

    fun getAllCompanies() {
        viewModelScope.launch {
            _cardData.value = companyRepository.getAllCompanies()
            Log.d("DATA", "${_cardData.value}")
        }
    }
}