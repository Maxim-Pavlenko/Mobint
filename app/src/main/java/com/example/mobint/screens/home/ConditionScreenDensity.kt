package com.example.mobint.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobint.util.Dimensions

@Composable
fun ConditionScreenDensity() {
    // Определяем плотность экрана
    if (LocalConfiguration.current.densityDpi <= 240) {
        Dimensions.apply {
            text0 = 20.sp
            text1 = 16.sp
            text2 = 10.sp
            text22 = 12.sp
            text3 = 8.sp
            margin1 = 12.dp
            margin2 = 6.dp
            margin22 = 34.dp
            iconSize = 12.dp
            sizePreloader = 32.dp
        }
    }
}