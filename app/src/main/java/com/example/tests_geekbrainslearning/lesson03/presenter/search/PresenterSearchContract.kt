package com.example.tests_geekbrainslearning.lesson03.presenter.search

import com.example.tests_geekbrainslearning.lesson03.presenter.PresenterContract

internal interface PresenterSearchContract : PresenterContract {
    fun searchGitHub(searchQuery: String)
}