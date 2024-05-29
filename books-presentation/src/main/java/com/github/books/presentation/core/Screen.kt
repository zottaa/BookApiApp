package com.github.books.presentation.core

sealed class Screen(val route: String) {
    data object List : Screen("list")
    data object Details : Screen("details")
}
