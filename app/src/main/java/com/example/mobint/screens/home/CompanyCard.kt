package com.example.mobint.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.mobint.R
import com.example.mobint.data.entities.CompanyItem
import com.example.mobint.data.entities.MobileAppDashboard
import com.example.mobint.ui.theme.LightGrey
import com.example.mobint.util.Dimensions


@Composable
fun CompanyCard(item: CompanyItem) {
    val isDialogOpen = remember {
        mutableStateOf(false)
    }
    val idCompany = remember {
        mutableStateOf("")
    }
    val nameButton = remember {
        mutableStateOf("")
    }
    val click: (String, String) -> Unit = { id, button ->
        idCompany.value = id
        nameButton.value = button
        isDialogOpen.value = true
    }
    if (isDialogOpen.value) {
        AlertDialogBox(
            onDismissRequest = {
                isDialogOpen.value = false
            },
            onConfirmButton = {
                isDialogOpen.value = false
            },
            dialogTitle = "Нажата кнопка " + nameButton.value,
            dialogText = "Company ID: " + idCompany.value
        )
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(android.graphics.Color.parseColor(item.mobileAppDashboard?.backgroundColor)),
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimensions.margin1)
    ) {
        Column(
            modifier = Modifier
                .padding(Dimensions.margin1)
        ) {
            HeadCard(item.mobileAppDashboard)

            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .size(1.dp)
                    .background(LightGrey)
            )

            MiddleCard(item)

            Box(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .size(1.dp)
                    .background(LightGrey)
            )

            ButtomCard(item, isDialogOpen, click)
        }
    }
}

@Composable
private fun ButtomCard(
    companyItem: CompanyItem,
    isDialogOpen: MutableState<Boolean>,
    click: (String, String) -> Unit
) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.ic_eye),
            contentDescription = null,
            alignment = Alignment.TopEnd,
            colorFilter = ColorFilter.tint(Color(android.graphics.Color.parseColor(companyItem.mobileAppDashboard?.mainColor))),
            modifier = Modifier
                .padding(top = Dimensions.margin1)
                .align(Alignment.CenterVertically)
                .size(Dimensions.iconSize)
                .clickable { click(companyItem.company.companyId, "Показать") }
        )
        Spacer(modifier = Modifier.width(Dimensions.margin22)) // Расстояние в 44dp между изображениями
        Image(
            painter = painterResource(id = R.drawable.ic_trash),
            contentDescription = null,
            alignment = Alignment.TopEnd,
            colorFilter = ColorFilter.tint(Color(android.graphics.Color.parseColor(companyItem.mobileAppDashboard?.accentColor))),
            modifier = Modifier
                .padding(top = Dimensions.margin1)
                .align(Alignment.CenterVertically)
                .size(Dimensions.iconSize)
                .clickable { click(companyItem.company.companyId, "Удалить") }
        )
        Spacer(modifier = Modifier.width(Dimensions.margin22))
        Button(
            onClick = {
                click(companyItem.company.companyId, "Подробнее")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LightGrey,
                contentColor = Color(android.graphics.Color.parseColor(companyItem.mobileAppDashboard?.mainColor))
            ),
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
private fun MiddleCard(item: CompanyItem) {
    Row(
        modifier = Modifier
            .padding(top = Dimensions.margin1)
    ) {
        Text(
            fontFamily = FontFamily(Font(R.font.segoe)),
            fontSize = Dimensions.text0,
            text = "${item.customerMarkParameters?.loyaltyLevel?.requiredSum ?: ""}",
            textAlign = TextAlign.Start,
            color = Color(android.graphics.Color.parseColor(item.mobileAppDashboard?.highlightTextColor))
        )
        Text(
            fontFamily = FontFamily(Font(R.font.segoe)),
            fontSize = Dimensions.text22,
            text = "баллов",
            color = Color(android.graphics.Color.parseColor(item.mobileAppDashboard?.textColor)),
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
                color = Color(android.graphics.Color.parseColor(item.mobileAppDashboard?.textColor)),
                textAlign = TextAlign.Start,
            )
            Text(
                fontFamily = FontFamily(Font(R.font.segoe)),
                fontSize = Dimensions.text2,
                text = "${item.customerMarkParameters?.loyaltyLevel?.cashToMark ?: ""} %",
                color = Color(android.graphics.Color.parseColor(item.mobileAppDashboard?.highlightTextColor)),
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
                color = Color(android.graphics.Color.parseColor(item.mobileAppDashboard?.textColor))
            )
            Text(
                fontFamily = FontFamily(Font(R.font.segoe)),
                fontSize = Dimensions.text2,
                text = item.customerMarkParameters?.loyaltyLevel?.name ?: "",
                textAlign = TextAlign.Start,
                color = Color(android.graphics.Color.parseColor(item.mobileAppDashboard?.highlightTextColor)),
                modifier = Modifier
                    .padding(top = Dimensions.margin2)
            )
        }
    }
}

@Composable
private fun HeadCard(mobileAppDashboard: MobileAppDashboard?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = Dimensions.margin2)
            .fillMaxWidth()
    ) {
        Text(
            fontFamily = FontFamily(Font(R.font.segoe)),
            fontSize = Dimensions.text1,
            text = mobileAppDashboard?.companyName ?: "",
            textAlign = TextAlign.Start,
            color = Color(android.graphics.Color.parseColor(mobileAppDashboard?.highlightTextColor)),
            modifier = Modifier
                .weight(1f)
        )
        Image(
            painter = rememberAsyncImagePainter(model = mobileAppDashboard?.logo),
            contentDescription = null,
            alignment = Alignment.TopEnd,
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .size(50.dp)
        )
    }
}
