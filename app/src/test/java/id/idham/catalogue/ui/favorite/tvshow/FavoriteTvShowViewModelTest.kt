package id.idham.catalogue.ui.favorite.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import id.idham.catalogue.data.CatalogueRepository
import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.ui.utils.TestCoroutineRule
import id.idham.catalogue.utils.SortUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class FavoriteTvShowViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var viewModel: FavoriteTvShowViewModel

    @Mock
    private lateinit var repository: CatalogueRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowViewModel(repository)
    }

    @Test
    fun `getFavoriteTvShows should be success`() {
        testCoroutineRule.runBlockingTest {
            val expected = MutableLiveData<PagedList<TvShowEntity>>()
            expected.value = PagedTestDataSources.snapshot()

            Mockito.`when`(repository.getFavoriteTvShows(SortUtils.TITLE))
                .thenReturn(expected)

            viewModel.getFavoriteTvShows(SortUtils.TITLE).observeForever(observer)
            Mockito.verify(observer).onChanged(expected.value)

            val expectedValue = expected.value
            val actualValue = viewModel.getFavoriteTvShows(SortUtils.TITLE).value
            assertEquals(expectedValue, actualValue)
            assertEquals(expectedValue?.snapshot(), actualValue?.snapshot())
            assertEquals(expectedValue?.size, actualValue?.size)
        }
    }

    @Test
    fun `getFavoriteTvShows should be success but data is empty`() {
        val expected = MutableLiveData<PagedList<TvShowEntity>>()
        expected.value = PagedTestDataSources.snapshot()

        Mockito.`when`(repository.getFavoriteTvShows(SortUtils.TITLE))
            .thenReturn(expected)

        viewModel.getFavoriteTvShows(SortUtils.TITLE).observeForever(observer)
        Mockito.verify(observer).onChanged(expected.value)

        val actualValueDataSize =
            viewModel.getFavoriteTvShows(SortUtils.TITLE).value?.size
        assertTrue(
            "size of data should be 0, actual is $actualValueDataSize",
            actualValueDataSize == 0
        )
    }

    class PagedTestDataSources private constructor(private val items: List<TvShowEntity>) :
        PositionalDataSource<TvShowEntity>() {
        companion object {
            fun snapshot(items: List<TvShowEntity> = listOf()): PagedList<TvShowEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams, callback: LoadInitialCallback<TvShowEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<TvShowEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}