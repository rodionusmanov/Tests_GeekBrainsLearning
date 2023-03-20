package com.example.tests_geekbrainslearning.lesson03

import androidx.lifecycle.Lifecycle
import com.example.tests_geekbrainslearning.TEST_ONE_NUMBER
import com.example.tests_geekbrainslearning.lesson03.presenter.search.PresenterSearchContract
import com.example.tests_geekbrainslearning.lesson03.presenter.search.SearchPresenter
import com.example.tests_geekbrainslearning.lesson03.view.search.ViewSearchContract
import com.geekbrains.tests.repository.GitHubRepository
import com.nhaarman.mockitokotlin2.times
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchPresenterTest {

    @Mock
    private lateinit var viewContract: ViewSearchContract

    @Mock
    private lateinit var gitHubRepository: GitHubRepository

    private lateinit var presenter: PresenterSearchContract

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(viewContract, gitHubRepository)
    }

    @Test
    fun presenter_OnAttachTest() {
        presenter.onAttach(Lifecycle.State.INITIALIZED)
        Mockito.verify(viewContract, times(TEST_ONE_NUMBER)).getLyfecycle("INITIALIZED")
    }

    @Test
    fun presenter_OnDetachTest() {
        presenter.onDetach(Lifecycle.State.DESTROYED)
        Mockito.verify(viewContract, times(TEST_ONE_NUMBER)).getLyfecycle("DESTROYED")
    }
}