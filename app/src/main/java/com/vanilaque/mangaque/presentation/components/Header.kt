package com.vanilaque.mangaque.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanilaque.mangaque.R

@Composable
fun Header(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchButtonClicked: () -> Unit,
    onSearchTitle: (String) -> Unit,
    onCloseSearchClicked: () -> Unit,
    isStateFocused: Boolean
) {
    if (isStateFocused)
        SearchWidget(
            text = text,
            onTextChange = onTextChange,
            onSearchClicked = onSearchTitle,
            onCloseClicked = onCloseSearchClicked
        )
    else
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.mock_avatar),
                contentDescription = "Avatar",
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "User name",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "Search",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onSearchButtonClicked() }
            )
        }
    //avatar -> user name                           search icon
    //when search clicked - open textfield and change icon location
}

//@Composable
//fun SearchField() {
//    var text by remember { mutableStateOf(TextFieldValue("")) }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(72.dp)
//            .padding(16.dp),
//        horizontalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        Image(
//            painter = painterResource(id = R.drawable.search),
//            contentDescription = "Search",
//            modifier = Modifier
//                .size(32.dp)
//                .align(CenterVertically)
//        )
//
//        TextField(value = text, onValueChange = { newText ->
//            text = newText
//        }, modifier = Modifier
//            .weight(1f)
//            .align(CenterVertically), shape = RoundedCornerShape(8.dp))
//    }
//}

@Composable
@Preview
fun SearchWidgetPreview() {
    SearchWidget(
        text = "",
        onTextChange = {},
        onSearchClicked = {},
        onCloseClicked = {}
    )
}

@Composable
fun SearchWidget(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = Color.White,
        shape = RoundedCornerShape(8.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = "TextField"
                },
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(alpha = ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
//            textStyle = TextStyle(
//                color = MaterialTheme.colors.topAppBarContentColor
//            )
//            ,
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(alpha = ContentAlpha.medium),
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search",
                        tint = Color.Black
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .semantics {
                            contentDescription = "CloseButton"
                        },
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        tint = Color.Black
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black
            )
        )
    }
}