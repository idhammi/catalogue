package id.idham.catalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import id.idham.catalogue.data.CatalogueRepository
import id.idham.catalogue.vo.Resource
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.data.remote.response.TvShowResponse
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
class TvShowViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var viewModel: TvShowViewModel

    @Mock
    private lateinit var repository: CatalogueRepository

    @Mock
    private lateinit var tvShowResponse: TvShowResponse

    @Mock
    private lateinit var observer: Observer<Resource<List<TvShowModel>>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(repository)
    }

    @Test
    fun `given success response when get tv shows`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            Mockito.`when`(repository.getTvShows()).thenReturn(tvShowResponse)

            // WHEN
            viewModel.dataTvShows.observeForever(observer)
            viewModel.getTvShows()

            // THEN
            Mockito.verify(observer).onChanged(Resource.loading())
            Mockito.verify(observer).onChanged(Resource.success(emptyList()))
            viewModel.dataTvShows.removeObserver(observer)
        }
    }

    @Test
    fun `given error response when get tv shows`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            val error = Error()
            Mockito.`when`(repository.getTvShows()).thenThrow(error)

            // WHEN
            viewModel.dataTvShows.observeForever(observer)
            viewModel.getTvShows()

            // THEN
            Mockito.verify(observer).onChanged(Resource.loading())
            Mockito.verify(observer).onChanged(Resource.error(null, null, error))
            viewModel.dataTvShows.removeObserver(observer)
        }
    }
}