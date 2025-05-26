package br.com.movieapp.search_movie_feature.domain.usecase

import androidx.paging.PagingConfig
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieSearchFactory
import br.com.movieapp.core.domain.model.PagingSourceMoviesSearchFactory
import br.com.movieapp.search_movie_feature.domain.repository.MovieSearchRepository
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetMovieSearchUseCaseImplTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var movieSearchRepository: MovieSearchRepository

    private val movies = MovieSearchFactory().create(poster = MovieSearchFactory.Poster.Avengers)

    private val pagingSourceFake = PagingSourceMoviesSearchFactory().create(
        listOf(movies)
    )

    private val getMovieSearchUseCaseImpl by lazy {
        GetMovieSearchUseCaseImpl(movieSearchRepository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() = runTest {
        //Given
        whenever(movieSearchRepository.getSearchMovies(query = ""))
            .thenReturn(pagingSourceFake)

        //When
        val result = getMovieSearchUseCaseImpl.invoke(
            params = GetMovieSearchUseCase.Params(
                query = "",
                PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        ).first()

        //Then
        verify(movieSearchRepository).getSearchMovies(query = "")
        Truth.assertThat(result).isNotNull()
    }

    @Test
    fun `should emit an empty stream when an exception is thrown when calling the invoke method`() = runTest {
        //Given
        val exception = RuntimeException()
        whenever(movieSearchRepository.getSearchMovies(query = ""))
            .thenThrow(exception)

        //When
        val result = getMovieSearchUseCaseImpl.invoke(
            params = GetMovieSearchUseCase.Params(
                query = "",
                PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20
                )
            )
        )

        //Then
        val resultList = result.toList()
        verify(movieSearchRepository).getSearchMovies(query = "")
        Truth.assertThat(resultList).isEmpty()
    }
}