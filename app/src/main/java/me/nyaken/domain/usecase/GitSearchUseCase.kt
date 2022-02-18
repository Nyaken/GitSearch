package me.nyaken.domain.usecase

import dagger.Reusable
import me.nyaken.common.DEFAULT_ITEM_SIZE
import me.nyaken.domain.repository.GitSearchRepository
import javax.inject.Inject

@Reusable
class GitSearchUseCase @Inject constructor(
    private val gitSearchRepository: GitSearchRepository
){

    operator fun invoke(
        query: String,
        sort: String?,
        order: String?,
        per_page: Int = DEFAULT_ITEM_SIZE,
        page: Int,
    ) = gitSearchRepository.searchGitRepositories(query, sort, order, per_page, page)

}