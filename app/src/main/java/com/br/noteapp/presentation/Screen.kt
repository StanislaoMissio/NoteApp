package com.br.noteapp.presentation

sealed class Screen(val route: String) {

    object MainScreen : Screen("main_screen")
    object EditScreen : Screen("edit_screen")

}