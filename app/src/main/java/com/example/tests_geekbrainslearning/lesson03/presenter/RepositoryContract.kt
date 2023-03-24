package com.example.tests_geekbrainslearning.lesson03.presenter

import com.example.tests_geekbrainslearning.lesson03.repository.RepositoryCallback
import com.geekbrains.tests.model.SearchResponse
import io.reactivex.rxjava3.core.Observable

interface RepositoryContract {

    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )

    fun searchGithub(
        query: String
    ): Observable<SearchResponse>
}
