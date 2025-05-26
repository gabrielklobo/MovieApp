package br.com.movieapp.movie_favorite_feature.domain.usecase

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.movie_favorite_feature.domain.repository.MovieFavoriteRepository
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMoviesFavoriteUseCaseImplTest {
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movies = listOf(
        MovieFactory().create(poster = MovieFactory.Poster.Avengers),
        MovieFactory().create(poster = MovieFactory.Poster.JohnWick)
    )

    private val getMovieFavoriteUseCase by lazy {
        GetMoviesFavoriteUseCaseImpl(movieFavoriteRepository)
    }

    @Test
    fun `should return Success from ResultStatus when the repository returns a list of movies`() = runTest {
        //Given
        whenever(movieFavoriteRepository.getMovies())
            .thenReturn(
                flowOf(movies)
            )

        //When
        val result = getMovieFavoriteUseCase.invoke().first()

        //Then
        Truth.assertThat(result).isNotEmpty()
        Truth.assertThat(result).contains(movies[1])
    }

    @Test
    fun `should emit an empty stream when exception is thrown when calling the invoke method`() = runTest {
        //Given
        val exception = RuntimeException()
        whenever(movieFavoriteRepository.getMovies())
            .thenThrow(exception)

        //When
        val result = getMovieFavoriteUseCase.invoke().toList()

        //Then
        verify(movieFavoriteRepository).getMovies()
        Truth.assertThat(result).isEmpty()
    }
}