package me.nyaken.gitsearch.home

import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.editorActionEvents
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.nyaken.common.SearchSort
import me.nyaken.gitsearch.BaseActivity
import me.nyaken.gitsearch.R
import me.nyaken.gitsearch.databinding.ActivityMainBinding
import me.nyaken.gitsearch.home.adapter.SearchResultAdapter
import me.nyaken.network.model.ErrorRes
import me.nyaken.utils.EventObserver

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()
    private val adapter: SearchResultAdapter by lazy {
        SearchResultAdapter(viewModel)
    }
    private var jobSearch: Job? = null
    private val imm: InputMethodManager
        get() = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

    override fun viewBinding() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    override fun setupObserve() {
        viewModel.showErrorToast.observe(this, EventObserver{
            Toast.makeText(this, getString(it), Toast.LENGTH_SHORT).show()
        })

        viewModel.queryData.observe(this, Observer{
            if(it.isNotEmpty()) {
                doSearch()
            }
        })

        binding.buttonOrder.clicks()
            .subscribeBy {
                viewModel.selectedOrder()
                onRefresh()
            }
            .addToDisposable()

        Observable.merge(
            binding.buttonSearch.clicks(),
            binding.edittextQuery.editorActionEvents()
        )
            .subscribeBy {
                viewModel.queryData(binding.edittextQuery.text.toString())
            }
            .addToDisposable()

    }

    override fun initLayout() {
        binding.spinnerSort.setSelection(0, false)

        binding.spinnerSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.selectedSortKey(parent?.getItemAtPosition(position) as SearchSort)
                onRefresh()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.list.adapter = adapter

        adapter.addLoadStateListener {
            if(it.refresh is LoadState.Error) {
                val errorRes =
                    Gson().fromJson(
                        (it.refresh as LoadState.Error).error.message.toString(),
                        ErrorRes::class.java
                    )

                val errorSnackbar = Snackbar.make(binding.root, errorRes.message, Snackbar.LENGTH_INDEFINITE)
                errorSnackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 5
                errorSnackbar.setAction(
                    getString(R.string.error_snackbar_throwable_detail)
                ) {
                    Toast.makeText(this, errorRes.documentation_url, Toast.LENGTH_LONG).show()
                    errorSnackbar.dismiss()
                }
                errorSnackbar.show()
            }
        }

    }

    private fun onRefresh() {
        adapter.refresh()

        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy {
                    it.refresh
                }
                .filter {
                    it.refresh is LoadState.NotLoading
                }
                .collect {
                    binding.list.scrollToPosition(0)
                }
        }

        currentFocus?.run {
            imm.hideSoftInputFromWindow(this.windowToken, 0)
            this.clearFocus()
        }

    }

    private fun doSearch() {
        jobSearch?.cancel()

        jobSearch = lifecycleScope.launch {
            lifecycleScope.launch {
                viewModel.listData.collectLatest {
                    adapter.submitData(it)
                }
            }
        }

        currentFocus?.run {
            imm.hideSoftInputFromWindow(this.windowToken, 0)
            this.clearFocus()
        }

    }
}