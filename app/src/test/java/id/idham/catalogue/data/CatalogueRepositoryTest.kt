package id.idham.catalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import id.idham.catalogue.data.local.LocalDataSource
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity
import id.idham.catalogue.data.remote.response.MovieModel
import id.idham.catalogue.data.remote.response.TvShowModel
import id.idham.catalogue.data.remote.source.RemoteDataSource
import id.idham.catalogue.ui.utils.LiveDataTestUtil
import id.idham.catalogue.ui.utils.PagedListUtil
import id.idham.catalogue.utils.ContextProviders
import id.idham.catalogue.utils.SortUtils
import id.idham.catalogue.vo.Pagination
import id.idham.catalogue.vo.Resource
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class CatalogueRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val context = Mockito.mock(ContextProviders::class.java)

    private val repository = FakeCatalogueRepository(remote, local, context)

    @Mock
    private lateinit var movieResponse: Pagination<MovieModel>

    @Mock
    private lateinit var tvShowResponse: Pagination<TvShowModel>

    @Mock
    private lateinit var favoriteMovies: DataSource.Factory<Int, MovieEntity>

    @Mock
    private lateinit var favoriteTvShow: DataSource.Factory<Int, TvShowEntity>

    private val dummyMovie = generateDummyMovies()[0]
    private val dummyTvShow = generateDummyTvShow()[0]
    private val dummyFavoriteMovies = generateDummyMovies()
    private val dummyFavoriteTvShows = generateDummyTvShow()

    @Test
    fun getMovies() {
        `when`(remote.getMovies()).thenReturn(movieResponse)
        val response = repository.getMovies()
        Assert.assertEquals(movieResponse.pagedList, response.pagedList)
        Assert.assertEquals(movieResponse.networkState, response.networkState)
    }

    @Test
    fun getMovieDetail() {
        val movieId = 1
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = generateDummyMovies()[0]
        `when`(local.getMovieById(movieId)).thenReturn(dummyEntity)

        val movieDetail = LiveDataTestUtil.getValue(repository.getMovieDetail(movieId))
        verify(local).getMovieById(movieId)
        Assert.assertNotNull(movieDetail)
        Assert.assertNotNull(movieDetail.data)
        Assert.assertEquals(dummyMovie, movieDetail.data)
    }

    @Test
    fun getTvShows() {
        `when`(remote.getTvShows()).thenReturn(tvShowResponse)
        val response = repository.getTvShows()
        Assert.assertEquals(movieResponse.pagedList, response.pagedList)
        Assert.assertEquals(movieResponse.networkState, response.networkState)
    }

    @Test
    fun getTvShowDetail() {
        val tvShowId = 1
        val dummyEntity = MutableLiveData<TvShowEntity>()
        dummyEntity.value = generateDummyTvShow()[0]
        `when`(local.getTvShowById(tvShowId)).thenReturn(dummyEntity)

        val tvShowDetail = LiveDataTestUtil.getValue(repository.getTvShowDetail(tvShowId))
        verify(local).getTvShowById(tvShowId)
        Assert.assertNotNull(tvShowDetail)
        Assert.assertNotNull(tvShowDetail.data)
        Assert.assertEquals(dummyTvShow, tvShowDetail.data)
    }

    @Test
    fun getFavoriteMovies() {
        `when`(local.getFavoriteMovies(SortUtils.TITLE)).thenReturn(favoriteMovies)
        repository.getFavoriteMovies(SortUtils.TITLE)

        val entities = Resource.success(PagedListUtil.mockPagedList(generateDummyMovies()))
        verify(local).getFavoriteMovies(SortUtils.TITLE)
        Assert.assertNotNull(entities)
        Assert.assertEquals(dummyFavoriteMovies.size.toLong(), entities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTvShows() {
        `when`(local.getFavoriteTvShows(SortUtils.TITLE)).thenReturn(favoriteTvShow)
        repository.getFavoriteTvShows(SortUtils.TITLE)

        val entities = Resource.success(PagedListUtil.mockPagedList(generateDummyTvShow()))
        verify(local).getFavoriteTvShows(SortUtils.TITLE)
        Assert.assertNotNull(entities)
        Assert.assertEquals(dummyFavoriteTvShows.size.toLong(), entities.data?.size?.toLong())
    }

    private fun generateDummyMovies(): List<MovieEntity> {
        val list = ArrayList<MovieEntity>()
        list.add(MovieEntity(1, "", "", "", 0.0, "", false, ""))
        list.add(MovieEntity(2, "", "", "", 0.0, "", false, ""))
        list.add(MovieEntity(3, "", "", "", 0.0, "", false, ""))
        list.add(MovieEntity(4, "", "", "", 0.0, "", false, ""))
        list.add(MovieEntity(5, "", "", "", 0.0, "", false, ""))
        return list
    }

    private fun generateDummyTvShow(): List<TvShowEntity> {
        val list = ArrayList<TvShowEntity>()
        list.add(TvShowEntity(1, "", "", "", 0.0, "", false, ""))
        list.add(TvShowEntity(2, "", "", "", 0.0, "", false, ""))
        list.add(TvShowEntity(3, "", "", "", 0.0, "", false, ""))
        list.add(TvShowEntity(4, "", "", "", 0.0, "", false, ""))
        list.add(TvShowEntity(5, "", "", "", 0.0, "", false, ""))
        return list
    }

}