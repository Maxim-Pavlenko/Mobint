package com.example.mobint.screens.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.mobint.R
import com.example.mobint.data.entities.CompanyItem
import com.example.mobint.ui.theme.Blue
import com.example.mobint.ui.theme.LightGrey
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun ListCompany(companyList: LazyPagingItems<CompanyItem>) {
    val openDialog = remember { mutableStateOf(true) }
    val isFirstLoad = remember { mutableStateOf(true) }
    val isSwipeLoading = remember { mutableStateOf(false) }
    val swipeRefreshState =
        rememberSwipeRefreshState(isRefreshing = isSwipeLoading.value)

    LaunchedEffect(key1 = companyList.loadState) {
        if (companyList.loadState.refresh is LoadState.Error || companyList.loadState.append is LoadState.Error) {
            isSwipeLoading.value = false
            openDialog.value = true
        }
    }

    Surface(
        color = LightGrey, modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Toolbar()
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { companyList.refresh() }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(all = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    companyList.apply {
                        when {
                            loadState.refresh is LoadState.Error -> {
                                Log.d("STATE", "REFRESH _ Error")
                                item {
                                    if (openDialog.value) {
                                        AlertDialogBox(
                                            onDismissRequest = {
                                                openDialog.value = false
                                            },
                                            onConfirmButton = {
                                                openDialog.value = false
                                            },
                                            dialogTitle = "Что-то пошло не так",
                                            dialogText = (loadState.refresh as LoadState.Error).error.message
                                                ?: ""
                                        )
                                    }
                                }
                            }

                            loadState.refresh is LoadState.Loading -> {
                                Log.d("STATE", "REFRESH _ Loading")
                                isSwipeLoading.value = true
                            }
                        }
                    }
                    items(
                        count = companyList.itemCount
                    ) { index ->
                        val company = companyList[index]
                        isSwipeLoading.value = false
                        company?.let { item ->
                            CompanyCard(item)
                        }
                    }

                    when (companyList.loadState.append) {
                        is LoadState.Loading -> {
                            Log.d("STATE", "APPEND _ Loading")
                            item { ProgressIndicator(Arrangement.Bottom) }
                        }
                        is LoadState.Error -> {
                            item {
                                if (openDialog.value) {
                                    AlertDialogBox(
                                        onDismissRequest = {
                                            openDialog.value = false
                                        },
                                        onConfirmButton = {
                                            openDialog.value = false
                                        },
                                        dialogTitle = "Что-то пошло не так",
                                        dialogText = (companyList.loadState.append as LoadState.Error).error.message
                                            ?: ""
                                    )
                                }
                            }
                        }

                        else -> {}
                    }

                }
            }
        }
    }
}

@Composable
private fun Toolbar() {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .background(Color.White)
    ) {
        Text(
            fontFamily = FontFamily(Font(R.font.segoe)),
            fontSize = Dimensions.text1,
            text = "Управление картами",
            textAlign = TextAlign.Center,
            color = Blue,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(Dimensions.margin1)
        )
    }
}

@Composable
fun AlertDialogBox(
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit,
    dialogTitle: String,
    dialogText: String
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest },
        confirmButton = {
            TextButton(onClick = onConfirmButton) {
                Text(text = "ОК")
            }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        icon = {

        }
    )
}
