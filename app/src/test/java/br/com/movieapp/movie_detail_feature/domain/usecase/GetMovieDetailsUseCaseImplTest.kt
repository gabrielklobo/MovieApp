package br.com.movieapp.movie_detail_feature.domain.usecase

import androidx.paging.PagingConfig
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieDetailsFactory
import br.com.movieapp.core.domain.model.MovieFactory
import br.com.movieapp.core.domain.model.PagingSourceMoviesFactory
import br.com.movieapp.core.util.ResultData
import br.com.movieapp.movie_detail_feature.domain.repository.MovieDetailsRepository
import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMovieDetailsUseCaseImplTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieDetailsRepository: MovieDetailsRepository

    private val movieFactory = MovieFactory().create(poster = MovieFactory.Poster.Avengers)

    private val movieDetailsFactory = MovieDetailsFactory().create(poster = MovieDetailsFactory.Poster.Avengers)

    private val pagingSourceFake = PagingSourceMoviesFactory().create(
        listOf(movieFactory)
    )

    private val getMovieDetailsUseCase by lazy {
        GetMovieDetailsUseCaseImpl(movieDetailsRepository)
    }

    @Test
    fun `should return Success from ResultStatus when get both requests return success`() = runTest {
        //Given
        whenever(movieDetailsRepository.getMovieDetails(movieId = movieFactory.id))
            .thenReturn(movieDetailsFactory)

        whenever(movieDetailsRepository.getMoviesSimilar(movieId = movieFactory.id))
            .thenReturn(pagingSourceFake)

        //When
        val result = getMovieDetailsUseCase.invoke(
            params = GetMovieDetailsUseCase.Params(
                movieId = movieFactory.id,
                pagingConfig = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        )

        //Then
        verify(movieDetailsRepository).getMovieDetails(movieId = movieFactory.id)
        verify(movieDetailsRepository).getMoviesSimilar(movieId = movieFactory.id)

        assertThat(result).isNotNull()
        assertThat(result is ResultData.Success).isTrue()
    }

    @Test
    fun `should return Error from ResultStatus when getting movieDetails requests return error`() = runTest {
        //Given
        val exception = RuntimeException()

        whenever(movieDetailsRepository.getMovieDetails(movieId = movieFactory.id))
            .thenThrow(exception)

        //When
        val result = getMovieDetailsUseCase.invoke(
            params = GetMovieDetailsUseCase.Params(
                movieId = movieFactory.id,
                pagingConfig = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        )

        //Then
        verify(movieDetailsRepository).getMovieDetails(movieId = movieFactory.id)
        assertThat(result is ResultData.Failure).isTrue()
    }

    @Test
    fun `should return Error from ResultStatus when get similar requests return error`() = runTest {
        //Given
        val exception = RuntimeException()

        whenever(movieDetailsRepository.getMoviesSimilar(movieId = movieFactory.id))
            .thenThrow(exception)

        //When
        val result = getMovieDetailsUseCase.invoke(
            params = GetMovieDetailsUseCase.Params(
                movieId = movieFactory.id,
                pagingConfig = PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        )

        //Then
        verify(movieDetailsRepository).getMoviesSimilar(movieId = movieFactory.id)
        assertThat(result is ResultData.Failure).isTrue()
    }
}