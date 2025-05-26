package br.com.movieapp.core.domain.model

class MovieSearchFactory {
    fun create(poster: Poster) = when (poster) {
        Poster.Avengers -> {
            MovieSearch(
                id = 1,
                voteAverage = 7.1,
                imageUrl = "ImageUrl"
            )
        }

        Poster.JohnWick -> {
            MovieSearch(
                id = 2,
                voteAverage = 7.9,
                imageUrl = "ImageUrl"
            )
        }
    }

    sealed class Poster {
        object Avengers : Poster()
        object JohnWick : Poster()
    }
}