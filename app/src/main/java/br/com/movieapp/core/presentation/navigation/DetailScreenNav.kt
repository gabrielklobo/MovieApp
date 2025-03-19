package br.com.movieapp.core.presentation.navigation

import br.com.movieapp.core.util.Constants.MOVIE_DETAIL_ARGUMENT_KEY

sealed class DetailScreenNav(val route: String) {
    object DetailScreen : DetailScreenNav(
        route = "movie_detail_destination?$MOVIE_DETAIL_ARGUMENT_KEY={$MOVIE_DETAIL_ARGUMENT_KEY}"
    ) {
        fun passMovieId(movieId: Int) =
            "movie_detail_destination?$MOVIE_DETAIL_ARGUMENT_KEY=$movieId"
    }
}