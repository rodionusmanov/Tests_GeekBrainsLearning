package com.example.tests_geekbrainslearning.lesson03.view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tests_geekbrainslearning.R
import com.example.tests_geekbrainslearning.databinding.DetailsActivityBinding
import com.example.tests_geekbrainslearning.lesson03.presenter.details.DetailsPresenter
import com.example.tests_geekbrainslearning.lesson03.presenter.details.PresenterDetailsContract
import java.util.*

class DetailsActivity : AppCompatActivity(), ViewDetailsContract {

    private val presenter: PresenterDetailsContract = DetailsPresenter(this)
    private lateinit var binding: DetailsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
    }

    private fun setUI() {
        presenter.onAttach(this.lifecycle.currentState)
        val count = intent.getIntExtra(TOTAL_COUNT_EXTRA, 0)
        presenter.setCounter(count)
        setCountText(count)
        binding.decrementButton.setOnClickListener { presenter.onDecrement() }
        binding.incrementButton.setOnClickListener { presenter.onIncrement() }
    }


    override fun setCount(count: Int) {
        setCountText(count)
    }

    override fun getLyfecycle(lifecycleState: String) {
        Toast.makeText(this, lifecycleState, Toast.LENGTH_SHORT).show()
//        binding.lifecycleStateTextView.text = lifecycleState
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach(this.lifecycle.currentState)
    }

    private fun setCountText(count: Int) {
        binding.totalCountTextView.text =
            String.format(
                Locale.getDefault(),
                getString(R.string.results_count), count
            )
    }

    companion object {

        const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"

        fun getIntent(context: Context, totalCount: Int): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(TOTAL_COUNT_EXTRA, totalCount)
            }
        }
    }
}