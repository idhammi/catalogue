package id.idham.catalogue.core.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {

    const val TITLE = "Title"
    const val RATING = "Rating"

    fun getSortedQuery(filter: String, table: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table WHERE isFavorite = 1 ")
        if (filter == TITLE) {
            val title = if (table == "tvshow") "name" else "title"
            simpleQuery.append("ORDER BY $title ASC")
        } else if (filter == RATING) {
            simpleQuery.append("ORDER BY voteAverage DESC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}