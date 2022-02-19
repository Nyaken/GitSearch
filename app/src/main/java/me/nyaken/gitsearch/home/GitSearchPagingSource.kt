package me.nyaken.gitsearch.home

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import me.nyaken.common.DEFAULT_ITEM_SIZE
import me.nyaken.domain.usecase.GitSearchUseCase
import me.nyaken.network.model.Item
import javax.inject.Inject

class GitSearchPagingSource @Inject constructor(
    private val gitSearchUseCase: GitSearchUseCase,
    private val query: String,
    private val sort: String?,
    private val order: String?,
    private val per_page: Int = DEFAULT_ITEM_SIZE
) : RxPagingSource<Int, Item>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Item>> {
        val currentPage: Int = params.key ?: 1

        return Single.fromObservable(
            gitSearchUseCase(query, sort, order, per_page, currentPage)
        )
            .subscribeOn(Schedulers.io())
            .map<LoadResult<Int, Item>> { result ->
                if(result.isSuccessful && result.body()?.items?.isNotEmpty() == true) {
                    val data = result.body()?.items ?: emptyList()
                    LoadResult.Page(
                        data = data,
                        prevKey = if (currentPage == 1) null else currentPage.minus(1),
                        nextKey = currentPage.plus(1)
                    )
                } else {
                    LoadResult.Error(Throwable(message = result.errorBody()?.string()))
                }
            }
            .onErrorReturn { e ->
                LoadResult.Error(e)
            }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}