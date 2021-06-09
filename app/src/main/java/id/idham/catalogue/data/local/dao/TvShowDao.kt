package id.idham.catalogue.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import id.idham.catalogue.data.local.entity.TvShowEntity

@Dao
interface TvShowDao {

    @Query("SELECT * FROM tvshow WHERE favorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tvshow WHERE id = :id")
    fun getTvShowById(id: Int): LiveData<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tvShows: TvShowEntity)

    @Update
    fun update(movie: TvShowEntity)

    @Delete
    fun delete(tvShow: TvShowEntity)

}