package com.example.tests_geekbrainslearning.lesson03.repository

internal interface RepositoryContract {
    fun searchGithub(
        query: String,
        callback: RepositoryCallback
    )
}
