package com.example.tests_geekbrainslearning.lesson03.presenter.search

import androidx.lifecycle.Lifecycle
import com.example.tests_geekbrainslearning.lesson03.repository.RepositoryCallback
import com.example.tests_geekbrainslearning.lesson03.repository.RepositoryContract
import com.example.tests_geekbrainslearning.lesson03.view.search.ViewSearchContract
import com.geekbrains.tests.model.SearchResponse
import com.geekbrains.tests.repository.GitHubRepository
import retrofit2.Response

internal class SearchPresenter internal constructor(
    private val viewContract: ViewSearchContract,
    private val repository: RepositoryContract
) : PresenterSearchContract, RepositoryCallback {

    override fun searchGitHub(searchQuery: String) {
        viewContract.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun onAttach(lifecycle: Lifecycle.State) {
        viewContract.getLyfecycle(lifecycle.name)
    }

    override fun onDetach(lifecycle: Lifecycle.State) {
        viewContract.getLyfecycle(lifecycle.name)
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            val searchResults = searchResponse?.searchResults
            val totalCount = searchResponse?.totalCount
            if (searchResults != null && totalCount != null) {
                viewContract.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract.displayError("Search results or total count are null")
            }
        } else {
            viewContract.displayError("Response is null or unsuccessful")
        }
    }

    override fun handleGitHubError() {
        viewContract.displayLoading(false)
        viewContract.displayError()
    }
}