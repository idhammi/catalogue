package id.idham.catalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.idham.catalogue.data.CatalogueRepository
import id.idham.catalogue.data.source.remote.Resource
import id.idham.catalogue.data.source.remote.response.MovieModel
import id.idham.catalogue.data.source.remote.response.TvShowModel
import id.idham.catalogue.ui.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var viewModel: DetailMovieViewModel

    @Mock
    private lateinit var repository: CatalogueRepository

    @Mock
    private lateinit var movieResponse: MovieModel

    @Mock
    private lateinit var tvShowResponse: TvShowModel

    @Mock
    private lateinit var observerMovie: Observer<Resource<MovieModel>>

    @Mock
    private lateinit var observerTvShow: Observer<Resource<TvShowModel>>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(repository)
        viewModel.setSelectedId("10")
    }

    @Test
    fun `given success response when get movie`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            Mockito.`when`(repository.getMovieDetail(10)).thenReturn(movieResponse)

            // WHEN
            viewModel.dataMovie.observeForever(observerMovie)
            viewModel.getMovie()

            // THEN
            Mockito.verify(observerMovie).onChanged(Resource.loading())
            Mockito.verify(observerMovie).onChanged(Resource.success(movieResponse))
            viewModel.dataMovie.removeObserver(observerMovie)
        }
    }

    @Test
    fun `given error response when get movie`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            val error = Error()
            Mockito.`when`(repository.getMovieDetail(10)).thenThrow(error)

            // WHEN
            viewModel.dataMovie.observeForever(observerMovie)
            viewModel.getMovie()

            // THEN
            Mockito.verify(observerMovie).onChanged(Resource.loading())
            Mockito.verify(observerMovie).onChanged(Resource.error(null, null, error))
            viewModel.dataMovie.removeObserver(observerMovie)
        }
    }

    @Test
    fun `given success response when get tv show`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            Mockito.`when`(repository.getTvShowDetail(10)).thenReturn(tvShowResponse)

            // WHEN
            viewModel.dataTvShow.observeForever(observerTvShow)
            viewModel.getTvShow()

            // THEN
            Mockito.verify(observerTvShow).onChanged(Resource.loading())
            Mockito.verify(observerTvShow).onChanged(Resource.success(tvShowResponse))
            viewModel.dataTvShow.removeObserver(observerTvShow)
        }
    }

    @Test
    fun `given error response when get tv show`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            val error = Error()
            Mockito.`when`(repository.getTvShowDetail(10)).thenThrow(error)

            // WHEN
            viewModel.dataTvShow.observeForever(observerTvShow)
            viewModel.getTvShow()

            // THEN
            Mockito.verify(observerTvShow).onChanged(Resource.loading())
            Mockito.verify(observerTvShow).onChanged(Resource.error(null, null, error))
            viewModel.dataTvShow.removeObserver(observerTvShow)
        }
    }
}