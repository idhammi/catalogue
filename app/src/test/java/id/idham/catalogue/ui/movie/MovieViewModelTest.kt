package id.idham.catalogue.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.idham.catalogue.data.CatalogueRepository
import id.idham.catalogue.data.source.remote.Resource
import id.idham.catalogue.data.source.remote.response.MovieModel
import id.idham.catalogue.data.source.remote.response.MovieResponse
import id.idham.catalogue.ui.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
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
    private lateinit var movieResponse: MovieResponse

    @Mock
    private lateinit var observer: Observer<Resource<List<MovieModel>>>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(repository)

    }

    @Test
    fun `given success response when get movies`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            `when`(repository.getMovies()).thenReturn(movieResponse)

            // WHEN
            viewModel.dataMovies.observeForever(observer)
            viewModel.getMovies()

            // THEN
            verify(observer).onChanged(Resource.loading())
            verify(observer).onChanged(Resource.success(emptyList()))
            viewModel.dataMovies.removeObserver(observer)
        }
    }

    @Test
    fun `given error response when get movies`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            val error = Error()
            `when`(repository.getMovies()).thenThrow(error)

            // WHEN
            viewModel.dataMovies.observeForever(observer)
            viewModel.getMovies()

            // THEN
            verify(observer).onChanged(Resource.loading())
            verify(observer).onChanged(Resource.error(null, null, error))
            viewModel.dataMovies.removeObserver(observer)
        }
    }
}