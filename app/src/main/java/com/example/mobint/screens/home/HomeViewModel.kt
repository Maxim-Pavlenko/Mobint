package com.example.mobint.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobint.data.repository.CompanyRepository
import com.example.mobint.entities.CompanyResponse
import com.example.mobint.util.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel (
    private val companyRepository: CompanyRepository
): ViewModel() {
    private val _cardData = MutableLiveData<CompanyResponse?>()
    val cardData: LiveData<CompanyResponse?> = _cardData

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Load)
    val state: StateFlow<State> = _state.asStateFlow()

    fun getAllCompanies() {
        viewModelScope.launch {
            _state.value = State.Load
            companyRepository.getAllCompanies()
            _state.value = State.Success
        }
    }
}