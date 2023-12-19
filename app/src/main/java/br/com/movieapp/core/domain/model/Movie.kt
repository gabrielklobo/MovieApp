package br.com.movieapp.core.domain.model

data class Movie(
    val id: Int,
    val title: Int,
    val voteAverage: Double = 0.0,
    val imageUrl: String = ""
)
