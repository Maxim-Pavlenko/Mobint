package com.example.mobint.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mobint.data.paging.CompanyPagingSource
import com.example.mobint.data.remote.RetrofitInstance
import com.example.mobint.data.repository.CompanyRepository
import com.example.mobint.entities.CompanyItem
import com.example.mobint.util.Constants.ITEMS_PER_PAGE
import com.example.mobint.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel (
    private val companyRepository: CompanyRepository
): ViewModel() {
   /* private val _cardData = MutableLiveData<CompanyResponse?>()
    val cardData: LiveData<CompanyResponse?> = _cardData*/

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Load)
    val state: StateFlow<State> = _state.asStateFlow()

    fun getFlow(): Flow<PagingData<CompanyItem>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {CompanyPagingSource(RetrofitInstance.retrofit)}
        ).flow
            .cachedIn(viewModelScope)
    }

    /*fun getAllCompanies() {
        viewModelScope.launch {
            _state.value = State.Load
            companyRepository.getAllCompanies()
            _state.value = State.Success
        }
    }*/
}