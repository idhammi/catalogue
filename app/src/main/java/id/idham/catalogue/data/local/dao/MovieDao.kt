package id.idham.catalogue.data.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import id.idham.catalogue.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getFavoriteMovies(query: SupportSQLiteQuery): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovieById(id: Int): Flow<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieEntity)

    @Update
    fun update(movie: MovieEntity)

}