package me.nyaken.gitsearch.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.nyaken.common.SearchOrder
import me.nyaken.common.SearchSort
import me.nyaken.domain.usecase.GitSearchUseCase
import me.nyaken.gitsearch.BaseViewModel
import me.nyaken.gitsearch.R
import me.nyaken.utils.Event
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gitSearchUseCase: GitSearchUseCase
): BaseViewModel() {

    val queryData = MutableLiveData<String>()

    val sortsData: List<SearchSort> = SearchSort.values().toList()

    private val _showErrorToast = MutableLiveData<Event<Int>>()
    val showErrorToast: LiveData<Event<Int>>
        get() = _showErrorToast

    private val _selectedSortKey = MutableLiveData(SearchSort.BEST_MATCH)
    fun selectedSortKey(item: SearchSort) {
        _selectedSortKey.value = item
        doSearch()
    }

    private val _selectedOrder = MutableLiveData(SearchOrder.DESC)
    val selectedOrder: LiveData<SearchOrder>
        get() = _selectedOrder
    fun selectedOrder() {
        _selectedOrder.value =
            if(_selectedOrder.value == SearchOrder.ASC) SearchOrder.DESC
            else SearchOrder.ASC

        doSearch()
    }

    fun clickSearch() {
        queryData.value?.let {
            doSearch()
        } ?: run {
            _showErrorToast.value = Event(R.string.error_input_query_placeholder)
        }
    }

    private fun doSearch() {
        queryData.value?.let {
            gitSearchUseCase(
                query = it,
                sort = _selectedSortKey.value?.toKey(),
                order = selectedOrder.value?.toKey(),
                page = 1
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {

                    }, onError = {

                    }
                )
                .addToDisposable()
        }
    }
}