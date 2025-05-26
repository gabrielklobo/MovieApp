package br.com.movieapp.movie_detail_feature.domain.repository

import androidx.paging.PagingSource
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.domain.model.MovieDetails

interface MovieDetailsRepository {
    suspend fun getMovieDetails(movieId: Int): MovieDetails
    fun getMoviesSimilar(movieId: Int): PagingSource<Int, Movie>
}