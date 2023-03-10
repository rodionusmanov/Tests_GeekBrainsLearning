package com.example.tests_geekbrainslearning.lesson03.presenter.details

import com.example.tests_geekbrainslearning.lesson03.PresenterContract

internal interface PresenterDetailsContract : PresenterContract {
    fun setCounter(count: Int)
    fun onIncrement()
    fun onDecrement()
}