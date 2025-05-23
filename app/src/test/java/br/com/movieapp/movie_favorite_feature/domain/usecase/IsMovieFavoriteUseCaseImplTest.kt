package br.com.movieapp.movie_favorite_feature.domain.usecase

import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_favorite_feature.domain.repository.MovieFavoriteRepository
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class IsMovieFavoriteUseCaseImplTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieFavoriteRepository: MovieFavoriteRepository

    private val movie = MovieFactory().create(MovieFactory.Poster.Avengers)

    private val isMovieFavoriteUseCase by lazy {
        IsMovieFavoriteUseCaseImpl(movieFavoriteRepository)
    }

    @Test
    fun `should return Success from ResultStatus when the repository returns success with the value equal true`() = runTest {
        //Given
        whenever(movieFavoriteRepository.isFavorite(any()))
            .thenReturn(true)

        //When
        val result = isMovieFavoriteUseCase.invoke(
            params = IsMovieFavoriteUseCase.Params(
                movieId = movie.id
            )
        ).first()

        //Then
        Truth.assertThat(result).isEqualTo(ResultData.Success(true))
    }

    @Test
    fun `should return Success from ResultStatus when the repository returns success with the value equal false`() = runTest {
        //Given
        whenever(movieFavoriteRepository.isFavorite(any()))
            .thenReturn(false)

        //When
        val result = isMovieFavoriteUseCase.invoke(
            params = IsMovieFavoriteUseCase.Params(
                movieId = movie.id
            )
        ).first()

        //Then
        Truth.assertThat(result).isEqualTo(ResultData.Success(false))
    }

    @Test
    fun `should return Failure from ResultStatus when the repository throws an exception`() = runTest {
        //Given
        val exception = RuntimeException()
        whenever(movieFavoriteRepository.isFavorite(any()))
            .thenThrow(exception)

        //When
        val result = isMovieFavoriteUseCase.invoke(
            params = IsMovieFavoriteUseCase.Params(
                movieId = movie.id
            )
        ).first()


        //Then
        Truth.assertThat(result).isEqualTo(ResultData.Failure(exception))
    }
}