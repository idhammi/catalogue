package id.idham.catalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.idham.catalogue.data.CatalogueRepository
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.ui.utils.TestCoroutineRule
import id.idham.catalogue.vo.Pagination
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var viewModel: MovieViewModel

    @Mock
    private lateinit var repository: CatalogueRepository

    @Mock
    private lateinit var movieResponse: Pagination<MovieModel>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(repository)
    }

    @Test
    fun `get movies`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            `when`(repository.getMovies()).thenReturn(movieResponse)

            // WHEN
            val response = viewModel.getMovies()

            // THEN
            Assert.assertEquals(movieResponse.pagedList, response.pagedList)
            Assert.assertEquals(movieResponse.networkState, response.networkState)
        }
    }
}