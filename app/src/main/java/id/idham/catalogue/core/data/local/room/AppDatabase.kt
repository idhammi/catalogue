package id.idham.catalogue.core.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import id.idham.catalogue.core.data.local.entity.MovieEntity
import id.idham.catalogue.core.data.local.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvShowDao(): TvShowDao

}