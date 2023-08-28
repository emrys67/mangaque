package com.vanilaque.mangaque

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.vanilaque.mangaque.presentation.components.Footer
import com.vanilaque.mangaque.presentation.components.FooterPath
import com.vanilaque.mangaque.presentation.components.Header
import com.vanilaque.mangaque.presentation.components.navigation.MangaNavigation
import com.vanilaque.mangaque.presentation.components.navigation.MangaScreens
import com.vanilaque.mangaque.service.StateManager
import com.vanilaque.mangaque.theme.MangaReaderTheme
import com.vanilaque.mangaque.worker.RefreshMangaWorker
import com.vanilaque.mangareader.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        if(viewModel.prefManager.beenAppCompletelyClosed){
//            Log.e("", "app been closed completely")
            val request = OneTimeWorkRequestBuilder<RefreshMangaWorker>().build()
            viewModel.workManager.enqueue(request)
        //}

        setContent {
            val isFocused by viewModel.isSearchFieldFocused
            val searchQuery by viewModel.searchText
            val footerPath by viewModel.footerPath
            val showBottomTopBars by StateManager.showBottomTopbars.collectAsState()
            val navController = rememberNavController()
            val permissionsState = rememberMultiplePermissionsState(
                permissions = listOf(
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )

            LaunchedEffect(footerPath) {
                if (viewModel.footerPath.value == FooterPath.LIBRARY)
                    navigateToLibraryScreen(navController)
                else if (viewModel.footerPath.value == FooterPath.CATALOG)
                    navigateToExploreScreen(navController)
            }

            LaunchedEffect(Unit) {
                if (!permissionsState.allPermissionsGranted) {
                    permissionsState.launchMultiplePermissionRequest()
                    Log.e("", "permissions request")
                } else {
                    Log.e("", "all permissions are granted")
                }
            }

            MangaReaderTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    if (showBottomTopBars)
                        Header(
                            text = searchQuery,
                            onTextChange = { viewModel.searchText.value = it },
                            onSearchTitle = {
                                //viewModel.searchQuerryForWebtoon()
                            },
                            onSearchButtonClicked = {
                                viewModel.isSearchFieldFocused.value =
                                    !viewModel.isSearchFieldFocused.value
                            }, onCloseSearchClicked = {
                                viewModel.isSearchFieldFocused.value =
                                    !viewModel.isSearchFieldFocused.value
                            },
                            isStateFocused = isFocused
                        )
                },
                    bottomBar = {
                        if (showBottomTopBars)
                            Footer({ viewModel.footerPath.value = it }, footerPath)
                    }) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background,
                    ) {
                        MangaNavigation(navController)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        viewModel.onApplicationClose()
        super.onDestroy()
    }
}

fun navigateToLibraryScreen(navController: NavController) {
    navController.navigate(MangaScreens.LibraryScreen.route)
}

fun navigateToExploreScreen(navController: NavController) {
    navController.navigate(MangaScreens.MainScreen.route)
}