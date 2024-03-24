package com.example.mobint.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.mobint.ui.theme.LightGrey
import com.example.mobint.util.State
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel()
) {
    ConditionScreenDensity()
    val companyList = homeViewModel.fetchCompanyItem().collectAsLazyPagingItems()
    ListCompany(companyList)
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    //ProgressIndicator()
}