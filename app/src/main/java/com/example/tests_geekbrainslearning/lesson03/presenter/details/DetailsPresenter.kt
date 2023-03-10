package com.example.tests_geekbrainslearning.lesson03.presenter.details

import androidx.lifecycle.Lifecycle
import com.example.tests_geekbrainslearning.lesson03.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    private val viewContract: ViewDetailsContract,
    private var count: Int = 0
) : PresenterDetailsContract {

    override fun setCounter(count: Int) {
        this.count = count
    }

    override fun onIncrement() {
        count++
        viewContract.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract.setCount(count)
    }

    override fun onAttach(lifecycle: Lifecycle.State) {
        viewContract.getLyfecycle(lifecycle.name)
    }

    override fun onDetach(lifecycle: Lifecycle.State) {
        viewContract.getLyfecycle(lifecycle.name)
    }
}