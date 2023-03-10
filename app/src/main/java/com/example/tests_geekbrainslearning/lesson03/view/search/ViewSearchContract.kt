package com.example.tests_geekbrainslearning.lesson03.view.search

import com.example.tests_geekbrainslearning.lesson03.view.ViewContract
import com.geekbrains.tests.model.SearchResult

internal interface ViewSearchContract : ViewContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )

    fun displayError()
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}