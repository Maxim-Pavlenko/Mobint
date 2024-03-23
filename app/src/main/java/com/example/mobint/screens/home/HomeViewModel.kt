package com.example.mobint.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mobint.data.repository.CompanyRepository
import com.example.mobint.data.entities.CompanyItem
import com.example.mobint.util.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class HomeViewModel (
    private val companyRepository: CompanyRepository
): ViewModel() {

    fun fetchCompanyItem(): Flow<PagingData<CompanyItem>>{
        return companyRepository.getAllCompany().cachedIn(viewModelScope)
    }
}