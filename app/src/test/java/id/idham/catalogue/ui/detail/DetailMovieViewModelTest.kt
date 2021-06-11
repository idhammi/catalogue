package id.idham.catalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import id.idham.catalogue.data.CatalogueRepository
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.ui.utils.TestCoroutineRule
import id.idham.catalogue.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
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
    private lateinit var observerMovie: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var observerTvShow: Observer<Resource<TvShowEntity>>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(repository)
        viewModel.setSelectedId(10)
    }

    @Test
    fun `get movie`() {
        testCoroutineRule.runBlockingTest {
            val expected = MutableLiveData<Resource<MovieEntity>>()
            expected.value = Resource.success(MovieEntity(10, "", "", "", 0.0, "", false, ""))

            `when`(repository.getMovieDetail(10)).thenReturn(expected)

            viewModel.getMovie().observeForever(observerMovie)
            Mockito.verify(observerMovie).onChanged(expected.value)

            val expectedValue = expected.value
            val actualValue = viewModel.getMovie().value
            Assert.assertEquals(expectedValue, actualValue)
        }
    }

    @Test
    fun `get tv show`() {
        testCoroutineRule.runBlockingTest {
            val expected = MutableLiveData<Resource<TvShowEntity>>()
            expected.value = Resource.success(TvShowEntity(10, "", "", "", 0.0, "", false, ""))

            `when`(repository.getTvShowDetail(10)).thenReturn(expected)

            viewModel.getTvShow().observeForever(observerTvShow)
            Mockito.verify(observerTvShow).onChanged(expected.value)

            val expectedValue = expected.value
            val actualValue = viewModel.getTvShow().value
            Assert.assertEquals(expectedValue, actualValue)
        }
    }
}