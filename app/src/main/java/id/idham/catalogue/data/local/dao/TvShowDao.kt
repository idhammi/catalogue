package id.idham.catalogue.data.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import id.idham.catalogue.data.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getFavoriteTvShows(query: SupportSQLiteQuery): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tvshow WHERE id = :id")
    fun getTvShowById(id: Int): Flow<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShows: TvShowEntity)

    @Update
    fun update(movie: TvShowEntity)

}