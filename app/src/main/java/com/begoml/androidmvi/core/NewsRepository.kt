package com.begoml.androidmvi.core

import com.begoml.androidmvi.core.model.NewsModel
import com.begoml.androidmvi.tools.ResultWrapper

interface NewsRepository {

    suspend fun getNews(): ResultWrapper<Throwable, List<NewsModel>>
}