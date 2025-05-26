package br.com.movieapp.movie_favorite_feature.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.movieapp.R
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.core.presentation.components.commom.MovieAppBar
import br.com.movieapp.movie_favorite_feature.presentation.components.MovieFavoriteContent

@Composable
fun MovieFavoriteScreen(
    movies: List<Movie>,
    navigateToDetailMovie: (Int) -> Unit,
) {

    Scaffold(
        topBar = {
            MovieAppBar(title = R.string.favorite_movies)
        }, content = { paddingValues ->
            MovieFavoriteContent(
                movies = movies,
                paddingValues = paddingValues,
                onClick = { movieId ->
                    navigateToDetailMovie(movieId)
                }
            )
        }
    )
}

@Preview
@Composable
private fun MovieFavoriteScreenPreview() {
    MovieFavoriteScreen(
        movies = emptyList(),
        navigateToDetailMovie = {}
    )
}