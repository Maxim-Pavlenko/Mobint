package com.example.mobint.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobint.R
import com.example.mobint.ui.theme.Black
import com.example.mobint.util.Dimensions

@Composable
fun EmptyView() {
    Column(
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            fontFamily = FontFamily(Font(R.font.segoe)),
            fontSize = Dimensions.text2,
            text = "Нет компаний",
            textAlign = TextAlign.Center,
            color = Black,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(top = Dimensions.margin2)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewEmptyView() {
    EmptyView()
}