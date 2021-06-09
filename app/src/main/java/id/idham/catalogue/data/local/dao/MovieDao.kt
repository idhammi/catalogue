package id.idham.catalogue.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import id.idham.catalogue.data.local.entity.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE favorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovieById(id: Int): LiveData<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity)

    @Update
    fun update(movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)

}