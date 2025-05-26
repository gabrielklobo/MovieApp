package br.com.movieapp.movie_popular_feature.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.movieapp.core.domain.model.Movie
import br.com.movieapp.movie_popular_feature.domain.repository.MoviePopularRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

interface GetPopularMoviesUseCase {
    operator fun invoke(params: Params): Flow<PagingData<Movie>>
    data class Params(val pagingConfig: PagingConfig)
}

class GetPopularMoviesUseCaseImpl @Inject constructor(
    private val repository: MoviePopularRepository
) : GetPopularMoviesUseCase {
    override fun invoke(params: GetPopularMoviesUseCase.Params): Flow<PagingData<Movie>> {
        return try {
            val pagingSource = repository.getPopularMovies()

            Pager(
                config = params.pagingConfig,
                pagingSourceFactory = {
                    pagingSource
                }
            ).flow
        } catch (e: Exception) {
            emptyFlow()
        }
    }
}