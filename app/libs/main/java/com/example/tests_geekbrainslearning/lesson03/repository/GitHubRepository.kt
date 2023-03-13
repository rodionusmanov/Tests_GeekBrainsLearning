package com.geekbrains.tests.repository

import com.example.tests_geekbrainslearning.lesson03.repository.RepositoryCallback
import com.example.tests_geekbrainslearning.lesson03.presenter.RepositoryContract
import com.geekbrains.tests.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class GitHubRepository(private val gitHubApi: GitHubApi) : RepositoryContract {

    interface GitHubRepositoryCallback {
        fun handleGitHubResponse(response: Response<SearchResponse?>?)
        fun handleGitHubError()
    }

    override fun searchGithub(query: String, callback: RepositoryCallback) {
        val call = gitHubApi.searchGithub(query)
        call?.enqueue(object : Callback<SearchResponse?> {

            override fun onResponse(
                call: Call<SearchResponse?>,
                response: Response<SearchResponse?>
            ) {
                callback.handleGitHubResponse(response)
            }

            override fun onFailure(
                call: Call<SearchResponse?>,
                t: Throwable
            ) {
                callback.handleGitHubError()
            }
        })
    }
}
