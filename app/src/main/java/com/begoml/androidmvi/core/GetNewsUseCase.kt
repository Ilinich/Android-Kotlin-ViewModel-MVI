package com.begoml.androidmvi.core

import com.begoml.androidmvi.core.model.NewsModel
import com.begoml.androidmvi.tools.ResultWrapper
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val repository: NewsRepository
) : UseCase<List<NewsModel>, UseCase.None>() {

    override suspend fun run(params: None): ResultWrapper<Failure, List<NewsModel>> = repository.getNews()
}