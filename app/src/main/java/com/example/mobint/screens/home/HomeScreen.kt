package com.example.mobint.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobint.R
import com.example.mobint.ui.theme.Black
import com.example.mobint.ui.theme.Blue
import com.example.mobint.ui.theme.LightGrey
import com.example.mobint.util.State
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        homeViewModel.getAllCompanies()
    }
    conditionScreenDensity()
    val state by homeViewModel.state.collectAsState()

    when(state) {
        State.Load -> {
            CircularProgressIndicator()
        }
        State.Error -> {

        }
        State.Success -> {

        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

    }
}

@Composable
private fun CircularProgressIndicator() {
    Surface(
        color = LightGrey
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(White)
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

            CircularProgressIndicator(
                color = Black,
                modifier = Modifier
                    .padding(top = Dimensions.margin1)
                    .width(Dimensions.sizePreloader)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                fontFamily = FontFamily(Font(R.font.segoe)),
                fontSize = Dimensions.text2,
                text = "Подгрузка компаний",
                textAlign = TextAlign.Center,
                color = Black,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(top = Dimensions.margin2)
            )
        }
    }
}

@Composable
private fun conditionScreenDensity() {
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

@Composable
fun CompanyCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.margin1)
    ) {
        Column(
            modifier = Modifier
                .padding(Dimensions.margin1)
        ) {
            HeadCard()

            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .size(1.dp)
                    .background(LightGrey)
            )

            MiddleCard()

            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .size(1.dp)
                    .background(LightGrey)
            )

            ButtomCard()
        }
    }
}

@Composable
private fun ButtomCard() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.ic_eye),
            contentDescription = null,
            alignment = Alignment.TopEnd,
            colorFilter = ColorFilter.tint(Blue),
            modifier = Modifier
                .padding(top = Dimensions.margin1)
                .align(Alignment.CenterVertically)
                .size(Dimensions.iconSize)
        )
        Spacer(modifier = Modifier.width(Dimensions.margin22)) // Расстояние в 44dp между изображениями
        Image(
            painter = painterResource(id = R.drawable.ic_trash),
            contentDescription = null,
            alignment = Alignment.TopEnd,
            colorFilter = ColorFilter.tint(Red),
            modifier = Modifier
                .padding(top = Dimensions.margin1)
                .align(Alignment.CenterVertically)
                .size(Dimensions.iconSize)
        )
        Spacer(modifier = Modifier.width(Dimensions.margin22))
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(containerColor = LightGrey, contentColor = Blue),
            shape = RoundedCornerShape(25),
            modifier = Modifier
                .padding(top = Dimensions.margin2)
                .weight(1f)
        ) {
            Text(
                fontFamily = FontFamily(Font(R.font.segoe)),
                fontSize = Dimensions.text2,
                text = "Подробнее"
            )
        }
    }
}

@Composable
private fun MiddleCard() {
    Row(
        modifier = Modifier
            .padding(top = Dimensions.margin1)
    ) {
        Text(
            fontFamily = FontFamily(Font(R.font.segoe)),
            fontSize = Dimensions.text0,
            text = "200",
            textAlign = TextAlign.Start,
            color = Black
        )
        Text(
            fontFamily = FontFamily(Font(R.font.segoe)),
            fontSize = Dimensions.text22,
            text = "баллов",
            color = Gray,
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(start = Dimensions.margin2)
        )
    }

    Row(
        modifier = Modifier
            .padding(top = Dimensions.margin1)
    ) {
        Column {
            Text(
                fontFamily = FontFamily(Font(R.font.segoe)),
                fontSize = Dimensions.text3,
                text = "Кешбэк",
                color = Gray,
                textAlign = TextAlign.Start,
            )
            Text(
                fontFamily = FontFamily(Font(R.font.segoe)),
                fontSize = Dimensions.text2,
                text = "1%",
                color = Black,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = Dimensions.margin2)
            )
        }

        Column(
            modifier = Modifier
                .padding(
                    start = Dimensions.margin22,
                    bottom = Dimensions.margin2
                )
        ) {
            Text(
                fontFamily = FontFamily(Font(R.font.segoe)),
                fontSize = Dimensions.text3,
                text = "Уровень",
                textAlign = TextAlign.Start,
                color = Gray
            )
            Text(
                fontFamily = FontFamily(Font(R.font.segoe)),
                fontSize = Dimensions.text2,
                text = "Базовый уровень тест",
                textAlign = TextAlign.Start,
                color = Black,
                modifier = Modifier
                    .padding(top = Dimensions.margin2)
            )
        }
    }
}

@Composable
private fun HeadCard() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = Dimensions.margin2)
            .fillMaxWidth()
    ) {
        Text(
            fontFamily = FontFamily(Font(R.font.segoe)),
            fontSize = Dimensions.text1,
            text = "Bonus Money",
            textAlign = TextAlign.Start,
            color = Black,
            modifier = Modifier
                .weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_splash),
            contentDescription = null,
            alignment = Alignment.TopEnd,
            modifier = Modifier
                .size(80.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview() {
    HomeScreen()
}