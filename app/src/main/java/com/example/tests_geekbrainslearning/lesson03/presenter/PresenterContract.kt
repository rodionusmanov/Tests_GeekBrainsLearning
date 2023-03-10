package com.example.tests_geekbrainslearning.lesson03.presenter

import androidx.lifecycle.Lifecycle

internal interface PresenterContract {
    fun onAttach(lifecycle: Lifecycle.State)
    fun onDetach(lifecycle: Lifecycle.State)
}