package id.idham.catalogue.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import id.idham.catalogue.data.local.dao.MovieDao
import id.idham.catalogue.data.local.dao.TvShowDao
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvShowDao(): TvShowDao

}