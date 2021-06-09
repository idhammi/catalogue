package id.idham.catalogue.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.idham.catalogue.data.local.AppDatabase.Companion.DB_VERSION
import id.idham.catalogue.data.local.dao.MovieDao
import id.idham.catalogue.data.local.dao.TvShowDao
import id.idham.catalogue.data.local.entity.MovieEntity
import id.idham.catalogue.data.local.entity.TvShowEntity

@Database(
    entities = [MovieEntity::class, TvShowEntity::class], version = DB_VERSION, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movie(): MovieDao
    abstract fun tvShow(): TvShowDao

    companion object {
        const val DB_VERSION = 1
        private const val DB_NAME = "catalogue.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: build(context).also { INSTANCE = it }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                .build()

    }
}