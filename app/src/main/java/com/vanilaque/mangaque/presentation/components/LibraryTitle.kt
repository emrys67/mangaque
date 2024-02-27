package com.vanilaque.mangaque.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanilaque.mangaque.R
import com.vanilaque.mangaque.data.model.Manga
import com.vanilaque.mangaque.theme.EXTRA_SMALL_PADDING
import com.vanilaque.mangaque.theme.MICRO_PADDING
import com.vanilaque.mangaque.theme.MangaPurple
import com.vanilaque.mangaque.util.millisToDateString

@Composable
fun LibraryMangaTitle(
    manga: Manga,
    onLikeClick: () -> Unit,
    onReadClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(IMAGE_HEIGHT_MEDIUM.dp)
            .fillMaxWidth()
            .padding(EXTRA_SMALL_PADDING),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TitlePictureFromServer(
            TitlePictureSize.Medium,
            imageUrl = manga.thumb,
            onLikeClick = onLikeClick,
            isLiked = manga.isInFavorites
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = manga.title,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold
                )

                if (manga.chaptersDownloaded > 0) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.downloaded),
                            contentDescription = "downloaded",
                            modifier = Modifier
                                .padding(MICRO_PADDING)
                                .size(24.dp)
                                .align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(color = Color.Black)
                        )
                        Text(
                            text = stringResource(
                                R.string.chapters_count,
                                manga.chaptersDownloaded
                            ),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                                .padding(start = EXTRA_SMALL_PADDING)
                        )
                    }
                }

                if (manga.lastChapterRead > 0) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.eye),
                            contentDescription = "seen",
                            modifier = Modifier
                                .padding(MICRO_PADDING)
                                .size(24.dp)
                                .align(Alignment.CenterVertically),
                            colorFilter = ColorFilter.tint(color = Color.Black)
                        )
                        Text(
                            text = stringResource(R.string.chapters_count, manga.lastChapterRead),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = EXTRA_SMALL_PADDING)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }

                manga.lastOpenedAt?.let {
                    Text(
                        text = stringResource(
                            R.string.chapters_last_opened,
                            millisToDateString(it)
                        ),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = EXTRA_SMALL_PADDING),
                        color = Color.Gray
                    )
                }

                manga.addedToFavoritesAt?.let {
                    Text(
                        text = stringResource(
                            R.string.chapters_added_to_favorite,
                            millisToDateString(it)
                        ),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = EXTRA_SMALL_PADDING),
                        color = Color.Gray
                    )
                }

            }

            Button(
                onClick = onReadClick, modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), elevation = ButtonDefaults.elevation(4.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MangaPurple)
            ) {
                Text(text = "Read now", fontSize = 22.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun SmallTitle(
    manga: Manga,
    onClick: () -> Unit,
    onLikeClick: () -> Unit,
) {
    Column(modifier = Modifier
        .width(IMAGE_WIDTH_SMALL.dp)
        .clickable { onClick() }) {
        TitlePictureFromServer(
            TitlePictureSize.SMALL,
            imageUrl = manga.thumb,
            onLikeClick,
            isLiked = manga.isInFavorites
        )
        Text(
            text = manga.title,
            fontSize = 14.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .width(IMAGE_WIDTH_SMALL.dp)
                .padding(top = MICRO_PADDING, start = MICRO_PADDING, end = MICRO_PADDING),
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun SearchedTitle(
    manga: Manga,
    onSearchClick: () -> Unit,
    onLikeClick: () -> Unit,
    isLiked: Boolean
) {
    Row(
        modifier = Modifier
            .height(IMAGE_HEIGHT_MEDIUM.dp)
            .fillMaxWidth()
            .padding(EXTRA_SMALL_PADDING),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TitlePictureFromServer(
            TitlePictureSize.Medium,
            imageUrl = manga.thumb,
            onLikeClick = onLikeClick,
            isLiked = isLiked
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = manga.title,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.SemiBold
                )

                Text(
                    text = manga.summary.substring(0, 465),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.Gray
                )
            }

            Button(
                onClick = onSearchClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MangaPurple),
                elevation = ButtonDefaults.elevation(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.read_now),
                    fontSize = 22.sp,
                    color = Color.White
                )
            }
        }
    }
}