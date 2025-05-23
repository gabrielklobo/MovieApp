package br.com.movieapp.core.paging

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import br.com.movieapp.TestDispatcherRule
import br.com.movieapp.core.domain.model.MovieSearch
import br.com.movieapp.core.domain.model.MovieSearchPagingFactory
import br.com.movieapp.core.domain.model.MovieSearchFactory
import br.com.movieapp.search_movie_feature.domain.source.MovieSearchRemoteDataSource
import com.nhaarman.mockitokotlin2.any
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
class MovieSearchPagingSourceTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Mock
    lateinit var remoteDataSource: MovieSearchRemoteDataSource

    private val movieSearchFactory = MovieSearchFactory()
    private val movieSearchPagingFactory = MovieSearchPagingFactory().create()

    private val moviePagingSource by lazy {
        MovieSearchPagingSource(
            query = "",
            remoteDataSource = remoteDataSource
        )
    }

    @Test
    fun `must return a success load result when load is called`() = runTest {
        //Given
        whenever(remoteDataSource.getSearchMovies(any(), any()))
            .thenReturn(movieSearchPagingFactory)

        //When
        val result = moviePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        val resultExpected = listOf(
            movieSearchFactory.create(MovieSearchFactory.Poster.Avengers),
            movieSearchFactory.create(MovieSearchFactory.Poster.JohnWick)
        )

        //Then
        assertThat(
            PagingSource.LoadResult.Page(
                data = resultExpected,
                prevKey = null,
                nextKey = null
            )
        ).isEqualTo(result)
    }

    @Test
    fun `must return a error load result when load is called`() = runTest {
        //Given
        val exception = RuntimeException()
        whenever(remoteDataSource.getSearchMovies(any(), any()))
            .thenThrow(exception)

        //When
        val result = moviePagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        //Then
        assertThat(PagingSource.LoadResult.Error<Int, MovieSearch>(exception)).isEqualTo(result)
    }

}