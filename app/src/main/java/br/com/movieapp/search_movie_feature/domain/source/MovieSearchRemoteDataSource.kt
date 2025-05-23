package br.com.movieapp.search_movie_feature.domain.source

import br.com.movieapp.core.domain.model.MovieSearchPaging
import br.com.movieapp.core.paging.MovieSearchPagingSource

interface MovieSearchRemoteDataSource {

    fun getSearchMoviePagingSource(query: String): MovieSearchPagingSource
    suspend fun getSearchMovies(page: Int, query: String): MovieSearchPaging
}