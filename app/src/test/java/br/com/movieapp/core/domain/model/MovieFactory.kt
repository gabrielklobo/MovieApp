package br.com.movieapp.core.domain.model

class MovieFactory {
    fun create(poster: Poster) = when (poster) {
        Poster.Avengers -> {
            Movie(
                id = 1,
                title = "Avengers",
                voteAverage = 7.1,
                imageUrl = "ImageUrl"
            )
        }

        Poster.JohnWick -> {
            Movie(
                id = 2,
                title = "JohnWick",
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