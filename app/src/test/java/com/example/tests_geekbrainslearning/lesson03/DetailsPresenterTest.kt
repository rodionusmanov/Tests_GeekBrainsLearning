package com.example.tests_geekbrainslearning.lesson03

import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tests_geekbrainslearning.TEST_ONE_NUMBER
import com.example.tests_geekbrainslearning.TEST_ZERO_NUMBER
import com.example.tests_geekbrainslearning.lesson03.presenter.details.DetailsPresenter
import com.example.tests_geekbrainslearning.lesson03.presenter.details.PresenterDetailsContract
import com.example.tests_geekbrainslearning.lesson03.view.details.ViewDetailsContract
import com.nhaarman.mockitokotlin2.times
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.R])
class DetailsPresenterTest {

    @Mock
    private lateinit var viewContract: ViewDetailsContract

    private lateinit var presenter: PresenterDetailsContract

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailsPresenter(viewContract, TEST_ZERO_NUMBER)
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