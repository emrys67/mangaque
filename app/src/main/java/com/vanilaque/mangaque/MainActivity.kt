package com.vanilaque.mangaque

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.material.*
import com.vanilaque.mangaque.util.MANGA_QUE_HOST
import com.vanilaque.mangaque.util.MANGA_QUE_KEY
import com.vanilaque.mangareader.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            viewModel.api.fetchImages(MANGA_QUE_KEY, MANGA_QUE_HOST, "6482ecf451e4e3e4f85d4062")
        }
    }
}