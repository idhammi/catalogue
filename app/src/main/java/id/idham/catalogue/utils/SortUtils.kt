package id.idham.catalogue.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {
    const val TITLE = "Title"
    const val RATING = "Rating"
    fun getSortedQuery(filter: String, table: String): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM $table WHERE favorite = 1 ")
        if (filter == TITLE) {
            simpleQuery.append("ORDER BY title ASC")
        } else if (filter == RATING) {
            simpleQuery.append("ORDER BY rating DESC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}