package me.nyaken.network

import io.reactivex.rxjava3.core.Observable
import me.nyaken.network.model.ResultRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiContainer {

    @GET("/search/repositories")
    fun searchRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("order") order: Int,
        @Query("per_page") size: Int,
        @Query("page") page: Int
    ): Observable<Response<ResultRes>>

}