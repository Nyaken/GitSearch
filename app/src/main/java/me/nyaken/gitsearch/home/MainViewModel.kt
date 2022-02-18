package me.nyaken.gitsearch.home

import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import me.nyaken.domain.usecase.GitSearchUseCase
import me.nyaken.gitsearch.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val gitSearchUseCase: GitSearchUseCase
): BaseViewModel() {

    fun doSearch() {
        gitSearchUseCase("scroll", "", "", page = 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {

                }, onError = {

                }
            )
            .addToDisposable()
    }
}