package com.vanilaque.mangaque.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.vanilaque.mangaque.R
import com.vanilaque.mangaque.theme.MangaPink
import com.vanilaque.mangaque.theme.MangaPurple

@Composable
fun Footer(onClick: (FooterPath) -> Unit = {}, footerPath: FooterPath) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(color = Color.White)
            .padding(16.dp)
            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.shopping), contentDescription = "Catalog",
            colorFilter = ColorFilter.tint(
                color = getColorForFooterItem(
                    FooterPath.CATALOG,
                    footerPath
                )
            ),
            modifier = Modifier
                .size(30.dp)
                .clickable { onClick(FooterPath.CATALOG) },
        )
        Image(
            painter = painterResource(id = R.drawable.book_open), contentDescription = "Catalog",
            colorFilter = ColorFilter.tint(
                color = getColorForFooterItem(
                    FooterPath.LIBRARY,
                    footerPath
                )
            ), modifier = Modifier
                .size(32.dp)
                .clickable { onClick(FooterPath.LIBRARY) }
        )
        Image(
            painter = painterResource(id = R.drawable.account_settings),
            contentDescription = "Catalog",
            colorFilter = ColorFilter.tint(
                color = getColorForFooterItem(
                    FooterPath.ACCOUNT,
                    footerPath
                )
            ),
            modifier = Modifier
                .size(34.dp)
                .padding(top = 4.dp)
                .clickable { onClick(FooterPath.ACCOUNT) }
        ) // keep in sharedPreferences
    }
}

fun getColorForFooterItem(footerPath: FooterPath, activeFooterPath: FooterPath) =
    if (footerPath == activeFooterPath) MangaPurple else MangaPink

enum class FooterPath {
    CATALOG,
    LIBRARY,
    ACCOUNT
}