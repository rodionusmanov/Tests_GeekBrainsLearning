package com.example.tests_geekbrainslearning.lesson03.view.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tests_geekbrainslearning.BuildConfig
import com.example.tests_geekbrainslearning.R
import com.example.tests_geekbrainslearning.databinding.ActivityMainRobolectricBinding
import com.example.tests_geekbrainslearning.lesson03.presenter.search.PresenterSearchContract
import com.example.tests_geekbrainslearning.lesson03.presenter.search.SearchPresenter
import com.example.tests_geekbrainslearning.lesson03.repository.FakeGitHubRepository
import com.example.tests_geekbrainslearning.lesson03.presenter.RepositoryContract
import com.example.tests_geekbrainslearning.lesson03.view.details.DetailsActivity
import com.geekbrains.tests.model.SearchResult
import com.geekbrains.tests.repository.GitHubApi
import com.geekbrains.tests.repository.GitHubRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), ViewSearchContract {

    private val adapter = SearchResultAdapter()
    private lateinit var binding: ActivityMainRobolectricBinding
    private val presenter: PresenterSearchContract = SearchPresenter(this, createRepository())
    private var totalCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter.onAttach(lifecycle.currentState)
        super.onCreate(savedInstanceState)
        binding = ActivityMainRobolectricBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
    }

    private fun setUI() {
        with(binding) {
            toDetailsActivityButton.setOnClickListener {
                startActivity(
                    DetailsActivity.getIntent(this@MainActivity, totalCount)
                )
            }
            searchTotalCountButton.setOnClickListener {
                val query = binding.searchEditText.text.toString()
                if (query.isNotBlank()) {
                    presenter.searchGitHub(query)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.enter_search_word),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            setQueryListener()
            setRecyclerView()
        }
    }

    private fun setRecyclerView() {
        with(binding) {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter
        }
    }

    private fun setQueryListener() {
        binding.searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = binding.searchEditText.text.toString()
                if (query.isNotBlank()) {
                    presenter.searchGitHub(query)
                    return@OnEditorActionListener true
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.enter_search_word),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OnEditorActionListener false
                }
            }
            false
        })
    }

    private fun createRepository(): RepositoryContract {
        return GitHubRepository(createRetrofit().create(GitHubApi::class.java))
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    ) {
        with(binding.totalCountTextView) {
            visibility = View.VISIBLE
            text =
                String.format(
                    Locale.getDefault(), getString(R.string.results_count),
                    totalCount
                )
        }
        this.totalCount = totalCount
        adapter.updateResults(searchResults)
    }


    override fun displayError() {
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(show: Boolean) {
        with(binding) {
            if (show) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
    }

    override fun getLyfecycle(lifecycleState: String) {
        Toast.makeText(this, lifecycleState, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onAttach(lifecycle.currentState)
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}