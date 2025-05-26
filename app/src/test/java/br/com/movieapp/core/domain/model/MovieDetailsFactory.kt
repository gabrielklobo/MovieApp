package br.com.movieapp.core.domain.model

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum

class MovieDetailsFactory {
    fun create(poster: Poster) = when (poster) {
        Poster.Avengers -> {
            MovieDetails(
                id = 1,
                title = "Avengers",
                voteAverage = 7.1,
                genres = listOf("Aventura, Ação", "Ficção Cientifica"),
                overview = LoremIpsum(100).values.first(),
                backdropPathUrl = "ImageUrl",
                releaseDate = "04/05/2012",
                duration = 143,
                voteCount = 7
            )
        }

        Poster.JohnWick -> {
            MovieDetails(
                id = 2,
                title = "John Wick",
                voteAverage = 7.9,
                genres = listOf("Ação", "Thriller", "Crime"),
                overview = LoremIpsum(100).values.first(),
                backdropPathUrl = "ImageUrl",
                releaseDate = "22/03/2023",
                duration = 169,
                voteCount = 7
            )
        }
    }

    sealed class Poster {
        object Avengers : Poster()
        object JohnWick : Poster()
    }
}