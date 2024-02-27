package com.vanilaque.mangaque.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanilaque.mangaque.R

@Composable
fun HorizontalRadioGroup(
    options: List<ChooseBox>,
    selected: ChooseBox,
    onClick: (ChooseBox) -> Unit,
    boxSize: ChooseBoxSize,
    selectedColor: Color,
    notSelectedColor: Color,
    modifier: Modifier
) {
    LazyRow(
        modifier = Modifier.then(modifier),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(options) { option ->
            val isSelected = option == selected
            Button(
                onClick = { onClick(option) },
                modifier = Modifier
                    .width(boxSize.width.dp)
                    .height(boxSize.height.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = if (isSelected) selectedColor else notSelectedColor,
                ),
                content = {
                    Text(
                        text = stringResource(id = option.resId),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)
            )
        }
    }
}

enum class ChooseBox(val resId: Int) {
    DOWNLOADED(R.string.downloaded),
    FAVORITES(R.string.favorites),
    DESCRIPTION(R.string.description),
    CHAPTERS(R.string.chapters),
    NEWEST(R.string.newest),
    PREVIOUS(R.string.previous),
    TOP(R.string.top)
}

enum class ChooseBoxSize(
    val width: Int,
    val height: Int = CHOOSE_BOX_HEIGHT_NORMAL,
    val fontSize: Int = CHOOSE_BOX_FONT
) {
    SMALL(CHOOSE_BOX_WIDTH_SMALL),
    MEDIUM(CHOOSE_BOX_WIDTH_MEDIUM),
    BIG(CHOOSE_BOX_WIDTH_BIG)
}

const val CHOOSE_BOX_HEIGHT_NORMAL = 48

const val CHOOSE_BOX_WIDTH_SMALL = 100
const val CHOOSE_BOX_WIDTH_MEDIUM = 140
const val CHOOSE_BOX_WIDTH_BIG = 150

const val CHOOSE_BOX_FONT = 16
