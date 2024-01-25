package com.example.myapplicationhp


class UIState<T> (
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)