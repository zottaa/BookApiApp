package com.github.bookapiapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.books.presentation.core.Screen
import com.github.books.presentation.details.BooksDetailsScreen
import com.github.books.presentation.details.BooksDetailsViewModel
import com.github.books.presentation.list.BooksListScreen
import com.github.books.presentation.list.BooksListViewModel
import com.github.books.uikit.theme.BookApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BookApiTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.List.route) {
                    composable(Screen.List.route) {
                        val viewModel: BooksListViewModel = hiltViewModel()
                        BooksListScreen(
                            viewModel = viewModel
                        ) { id ->
                            navController.navigate("${Screen.Details}/$id")
                        }
                    }
                    composable(
                        "${Screen.Details.route}/{$VOLUME_INFO_ID_KEY}",
                        arguments = listOf(navArgument(VOLUME_INFO_ID_KEY) {
                            type = NavType.LongType
                        })
                    ) { backStackEntry ->
                        backStackEntry.arguments?.let {
                            val viewModel: BooksDetailsViewModel = hiltViewModel()
                            BooksDetailsScreen(
                                volumeInfoId = it.getLong(
                                    VOLUME_INFO_ID_KEY
                                ),
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val VOLUME_INFO_ID_KEY = "volumeInfoId"
    }
}
