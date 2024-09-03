package br.com.movieapp.movie_detail_feature.presentation

sealed class MovieDetailEvent {
    data class GetMovieDetail(val movieId: Int) : MovieDetailEvent()
}