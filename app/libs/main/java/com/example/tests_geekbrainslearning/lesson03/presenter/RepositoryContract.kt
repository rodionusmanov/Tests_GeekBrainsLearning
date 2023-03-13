package com.example.tests_geekbrainslearning.lesson03.presenter

import com.example.tests_geekbrainslearning.lesson03.repository.RepositoryCallback

internal interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}
