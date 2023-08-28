package com.vanilaque.mangaque.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TitlePictureFromServer(
    titlePictureSize: TitlePictureSize = TitlePictureSize.Medium,
    imageUrl: String,
    onLikeClick: () -> Unit,
    isLiked: Boolean
) {
    Box(
        modifier = Modifier
            .width(titlePictureSize.imageWidth.dp)
            .height(titlePictureSize.imageHeight.dp),
    ) {
        TitleImage(imageUrl)
        LikeField(
            modifier = Modifier.align(Alignment.BottomCenter),
            likeSize = titlePictureSize.likeSize.dp,
            fieldWidth = titlePictureSize.fieldWidth.dp,
            fieldHeight = titlePictureSize.fieldHeight.dp,
            onLikeClick = onLikeClick,
            isLiked = isLiked
        )
    }
}

@Composable
fun TitlePictureFromDb(
    titlePictureSize: TitlePictureSize = TitlePictureSize.Medium,
    bitMap: Bitmap,
    onLikeClick: () -> Unit,
    isLiked: Boolean
) {
    Box(
        modifier = Modifier
            .width(titlePictureSize.imageWidth.dp)
            .height(titlePictureSize.imageHeight.dp),
    ) {
        TitleImageFromDb(bitmap = bitMap)
        LikeField(
            modifier = Modifier.align(Alignment.BottomCenter),
            likeSize = titlePictureSize.likeSize.dp,
            fieldWidth = titlePictureSize.fieldWidth.dp,
            fieldHeight = titlePictureSize.fieldHeight.dp,
            onLikeClick = onLikeClick,
            isLiked = isLiked
        )
    }
}

enum class TitlePictureSize(
    val likeSize: Int, val fieldWidth: Int, val fieldHeight: Int, val imageHeight: Int,
    val imageWidth: Int
) {
    SMALL(
        likeSize = LIKE_SIZE_SMALL,
        fieldWidth = LIKE_FIELD_WIDTH_SMALL,
        fieldHeight = LIKE_FIELD_HEIGHT_SMALL,
        imageHeight = IMAGE_HEIGHT_SMALL,
        imageWidth = IMAGE_WIDTH_SMALL
    ),
    Medium(
        likeSize = LIKE_SIZE_MEDIUM,
        fieldWidth = LIKE_FIELD_WIDTH_MEDIUM,
        fieldHeight = LIKE_FIELD_HEIGHT_MEDIUM,
        imageHeight = IMAGE_HEIGHT_MEDIUM,
        imageWidth = IMAGE_WIDTH_MEDIUM
    )
}

const val LIKE_SIZE_SMALL = 32
const val LIKE_SIZE_MEDIUM = 48

const val LIKE_FIELD_WIDTH_SMALL = 100
const val LIKE_FIELD_WIDTH_MEDIUM = 180

const val LIKE_FIELD_HEIGHT_SMALL = 40
const val LIKE_FIELD_HEIGHT_MEDIUM = 50

const val IMAGE_HEIGHT_SMALL = 180
const val IMAGE_HEIGHT_MEDIUM = 300

const val IMAGE_WIDTH_SMALL = 100
const val IMAGE_WIDTH_MEDIUM = 180

