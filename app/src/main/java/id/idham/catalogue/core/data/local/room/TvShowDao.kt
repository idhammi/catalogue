package id.idham.catalogue.core.data.local.room

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import id.idham.catalogue.core.data.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {

    @RawQuery(observedEntities = [TvShowEntity::class])
    fun getFavoriteTvShows(query: SupportSQLiteQuery): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM tvshow WHERE tvShowId = :id")
    fun getTvShowById(id: Int): Flow<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShows: TvShowEntity)

    @Update
    fun update(movie: TvShowEntity)

}