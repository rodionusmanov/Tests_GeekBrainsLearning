package com.example.tests_geekbrainslearning.lesson03.view.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tests_geekbrainslearning.R
import com.example.tests_geekbrainslearning.databinding.ActivityMainRobolectricBinding
import com.example.tests_geekbrainslearning.lesson03.presenter.RepositoryContract
import com.example.tests_geekbrainslearning.lesson03.presenter.search.PresenterSearchContract
import com.example.tests_geekbrainslearning.lesson03.presenter.search.SearchPresenter
import com.example.tests_geekbrainslearning.lesson03.view.details.DetailsActivity
import com.example.tests_geekbrainslearning.lesson03.viewModel.ScreenState
import com.example.tests_geekbrainslearning.lesson03.viewModel.SearchViewModel
import com.geekbrains.tests.model.SearchResult
import com.geekbrains.tests.repository.GitHubApi
import com.geekbrains.tests.repository.GitHubRepository
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), ViewSearchContract {

    private val adapter = SearchResultAdapter()
    private lateinit var binding: ActivityMainRobolectricBinding
    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private var totalCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRobolectricBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI()
        viewModel.subscribeToLiveData().observe(this) { onStateChange(it) }
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
                    viewModel.searchGitHub(query)
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
                    viewModel.searchGitHub(query)
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
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
                    Locale.getDefault(), getString(R.string.results_count), totalCount
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
    }

    companion object {
        const val BASE_URL = "https://api.github.com"
    }

    private fun onStateChange(screenState: ScreenState) {
        when (screenState) {
            is ScreenState.Working -> {
                val searchResponse = screenState.searchResponse
                val totalCount = searchResponse.totalCount
                binding.progressBar.visibility = View.GONE
                with(binding.totalCountTextView) {
                    visibility = View.VISIBLE
                    text =
                        String.format(
                            Locale.getDefault(),
                            getString(R.string.results_count),
                            totalCount
                        )
                }
                this.totalCount = totalCount!!
                adapter.updateResults(searchResponse.searchResults!!)
            }
            is ScreenState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            is ScreenState.Error -> {
                binding.progressBar.visibility = View.GONE
                Toast.makeText(this, screenState.error.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}