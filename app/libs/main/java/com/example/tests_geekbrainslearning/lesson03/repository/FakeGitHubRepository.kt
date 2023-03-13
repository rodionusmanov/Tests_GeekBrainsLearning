package com.example.tests_geekbrainslearning.lesson03.repository

import com.example.tests_geekbrainslearning.lesson03.presenter.RepositoryContract
import com.geekbrains.tests.model.SearchResponse
import retrofit2.Response

internal class FakeGitHubRepository : RepositoryContract {
    override fun searchGithub(
        query: String,
        callback: RepositoryCallback
    ) {
        callback.handleGitHubResponse(Response.success(SearchResponse(42, listOf())))
    }
}
