package id.idham.catalogue.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import id.idham.catalogue.data.CatalogueRepository
import id.idham.catalogue.data.remote.response.TvShowModel
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
    private lateinit var tvShowResponse: Pagination<TvShowModel>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(repository)
    }

    @Test
    fun `get tv shows`() {
        testCoroutineRule.runBlockingTest {
            // GIVEN
            Mockito.`when`(repository.getTvShows()).thenReturn(tvShowResponse)

            // WHEN
            val response = viewModel.getTvShows()

            // THEN
            Assert.assertEquals(tvShowResponse.pagedList, response.pagedList)
            Assert.assertEquals(tvShowResponse.networkState, response.networkState)
        }
    }
}