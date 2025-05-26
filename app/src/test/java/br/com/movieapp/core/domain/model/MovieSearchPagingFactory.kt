package br.com.movieapp.core.domain.model

class MovieSearchPagingFactory {

    fun create() = MovieSearchPaging(
        page = 1,
        totalPages = 1,
        totalResults = 2,
        movies = listOf(
            MovieSearch(
                id = 1,
                voteAverage = 7.1,
                imageUrl = "ImageUrl"
            ),
            MovieSearch(
                id = 2,
                voteAverage = 7.9,
                imageUrl = "ImageUrl"
            )
        )
    )
}