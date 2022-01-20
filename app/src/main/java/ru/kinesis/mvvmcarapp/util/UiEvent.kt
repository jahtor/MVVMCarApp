package ru.kinesis.mvvmcarapp.util

// описание событий

sealed class UiEvent{
    // событие которое посылаем UI когда хотим возвратиться назад
    object PopBackStack: UiEvent()

    // событие которое посылаем UI когда хотим перецти на другой экран
    data class Navigate(val route: String): UiEvent()

    // событие которое посылаем UI для показа Snackbar
    data class ShowSnackbar(
        val message: String,
        val action: String? = null
    ): UiEvent()
}
