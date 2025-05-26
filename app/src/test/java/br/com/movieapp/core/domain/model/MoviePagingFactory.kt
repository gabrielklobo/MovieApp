package br.com.movieapp.core.domain.model

class MoviePagingFactory {

    fun create() = MoviePaging(
        page = 1,
        totalPages = 1,
        totalResults = 2,
        movies = listOf(
            Movie(
                id = 1,
                title = "Avengers",
                voteAverage = 7.1,
                imageUrl = "ImageUrl"
            ),
            Movie(
                id = 2,
                title = "JohnWick",
                voteAverage = 7.9,
                imageUrl = "ImageUrl"
            )
        )
    )
}