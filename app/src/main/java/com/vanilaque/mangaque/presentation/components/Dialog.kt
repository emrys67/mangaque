package com.vanilaque.mangaque.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.vanilaque.mangaque.R
import com.vanilaque.mangaque.presentation.screens.titleinfo.TitleInfoViewModel
import com.vanilaque.mangaque.theme.MEDIUM_PADDING
import com.vanilaque.mangaque.theme.MangaPurple
import com.vanilaque.mangaque.theme.Purple200

@Composable
fun MangaSaveInProgressDialog(
    modifier: Modifier = Modifier,
    state: TitleInfoViewModel.ViewModelState,
    onClose: () -> Unit
) {
    if (state is TitleInfoViewModel.ViewModelState.DownloadingState) {
        Dialog(onDismissRequest = onClose) {
            Card(
                modifier = modifier
                    .padding(horizontal = MEDIUM_PADDING)
                    .fillMaxWidth(),
                backgroundColor = MaterialTheme.colors.background,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    horizontalAlignment = CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .padding(MEDIUM_PADDING)
                        .fillMaxWidth()
                        .height(160.dp)
                ) {
                    when (state) {
                        is TitleInfoViewModel.ViewModelState.DownloadingState.MangaIsSaving -> {
                            CircularProgressIndicator(
                                color = MaterialTheme.colors.primaryVariant,
                                modifier = Modifier
                                    .size(64.dp)
                                    .align(CenterHorizontally)
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = stringResource(R.string.manga_downloading_message),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = stringResource(R.string.manga_downloading_warning),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.error
                            )
                        }

                        is TitleInfoViewModel.ViewModelState.DownloadingState.MangaSaved -> {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "success",
                                modifier = Modifier
                                    .size(64.dp)
                                    .align(CenterHorizontally),
                                tint = MaterialTheme.colors.primaryVariant
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = stringResource(R.string.manga_downloaded_message),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                            )
                            ClickableText(
                                text = AnnotatedString(stringResource(R.string.close)),
                                onClick = { onClose() },
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colors.primaryVariant
                                ),
                                modifier = Modifier.align(CenterHorizontally)
                            )
                        }

                        is TitleInfoViewModel.ViewModelState.DownloadingState.MangaSaveError -> {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "error",
                                modifier = Modifier
                                    .size(64.dp)
                                    .align(CenterHorizontally),
                                tint = MaterialTheme.colors.primaryVariant
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = stringResource(R.string.manga_downloading_error),
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            ClickableText(
                                text = AnnotatedString(stringResource(R.string.close)),
                                onClick = { onClose() },
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    color = MaterialTheme.colors.primaryVariant
                                ),
                                modifier = Modifier.align(CenterHorizontally)
                            )
                        }
                    }
                }
            }
        }
    }
}