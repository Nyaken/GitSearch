package me.nyaken.domain.repository

import io.reactivex.rxjava3.core.Observable
import me.nyaken.network.ApiContainer
import me.nyaken.network.model.ResultRes
import retrofit2.Response

class GitSearchRepository(
    private val api: ApiContainer
) {

    fun searchGitRepositories(
        query: String,
        sort: String?,
        order: String?,
        per_page: Int,
        page: Int,
    ): Observable<Response<ResultRes>> {
        return api.searchRepositories(query, sort, order, per_page, page)
    }

}