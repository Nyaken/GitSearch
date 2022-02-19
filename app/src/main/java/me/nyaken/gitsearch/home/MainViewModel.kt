package me.nyaken.gitsearch.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import me.nyaken.common.DEFAULT_ITEM_SIZE
import me.nyaken.common.SearchOrder
import me.nyaken.common.SearchSort
import me.nyaken.domain.usecase.GitSearchUseCase
import me.nyaken.gitsearch.BaseViewModel
import me.nyaken.gitsearch.R
import me.nyaken.network.model.Item
import me.nyaken.utils.Event
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gitSearchUseCase: GitSearchUseCase
): BaseViewModel() {

    val listData = Pager(PagingConfig(pageSize = DEFAULT_ITEM_SIZE)) {
        GitSearchPagingSource(
            gitSearchUseCase,
            queryData.value.toString(),
            _selectedSortKey.value?.toKey(),
            selectedOrder.value?.toKey()
        )
    }.flow.cachedIn(viewModelScope)

    private val _queryData = MutableLiveData<String>("")
    val queryData: LiveData<String>
        get() = _queryData

    fun queryData(item: String) {
        if(item.isNotBlank()) {
            _queryData.value = item
        } else {
            _showErrorToast.value = Event(R.string.error_input_query_placeholder)
        }
    }

    val sortsData: List<SearchSort> = SearchSort.values().toList()

    private val _showErrorToast = MutableLiveData<Event<Int>>()
    val showErrorToast: LiveData<Event<Int>>
        get() = _showErrorToast

    private val _selectedSortKey = MutableLiveData(SearchSort.BEST_MATCH)

    fun selectedSortKey(item: SearchSort) {
        _selectedSortKey.value = item
    }

    private val _selectedOrder = MutableLiveData(SearchOrder.DESC)
    val selectedOrder: LiveData<SearchOrder>
        get() = _selectedOrder

    fun selectedOrder() {
        _selectedOrder.value =
            if(_selectedOrder.value == SearchOrder.ASC) {
                SearchOrder.DESC
            } else {
                SearchOrder.ASC
            }
    }

    private val _clickRepositoryItem = MutableLiveData<Event<Item>>()
    val clickRepositoryItem: LiveData<Event<Item>>
        get() = _clickRepositoryItem

    fun clickRepositoryItem(item: Item) {
        _clickRepositoryItem.value = Event(item)
    }

}