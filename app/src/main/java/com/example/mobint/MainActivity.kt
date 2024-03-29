package com.example.mobint

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.mobint.navigation.SetupNavGraph
import com.example.mobint.screens.home.HomeScreen
import com.example.mobint.ui.theme.MobintTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobintTheme {
                val navContreller = rememberNavController()
                SetupNavGraph(navController = navContreller)
            }
        }
    }
}