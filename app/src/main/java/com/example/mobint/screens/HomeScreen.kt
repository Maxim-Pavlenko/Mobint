package com.example.mobint.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mobint.R
import com.example.mobint.ui.theme.Black
import com.example.mobint.ui.theme.Blue
import com.example.mobint.ui.theme.LightGrey


@Composable
fun HomeScreen() {
    // Определяем плотность экрана
    if (LocalConfiguration.current.densityDpi <= 240) {
        Dimensions.apply {
            text0 = 20.sp
            text1 = 16.sp
            text2 = 10.sp
            text22 = 12.sp
            margin1 = 12.dp
            margin2 = 6.dp
            sizePreloader = 32.dp
        }
    }
    Surface (
        color = LightGrey
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
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

        /*LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

        }*/
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
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
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
                        .padding(start = Dimensions.margin1)
                )
                Image(
                    painter = painterResource(id = R.drawable.ic_splash),
                    contentDescription = null,
                    alignment = Alignment.TopEnd,
                    modifier = Modifier
                        .size(80.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = Dimensions.margin1)
            ) {
                Text(
                    fontFamily = FontFamily(Font(R.font.segoe)),
                    fontSize = Dimensions.text0,
                    text = "200",
                    textAlign = TextAlign.Start,
                    color = Black,
                    modifier = Modifier
                        .padding(start = Dimensions.margin1)
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
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun Preview() {
    CompanyCard()
}