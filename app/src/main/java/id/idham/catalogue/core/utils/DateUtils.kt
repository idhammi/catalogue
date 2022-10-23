package id.idham.catalogue.core.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private const val YEAR_MONTH_DAY_DASH_FORMAT = "yyyy-MM-dd"
    private const val DAY_FULL_FORMAT = "EEEE, d MMMM yyyy"

    /**
     * eg: 2020-02-01 to Tuesday, 23 July 2019
     */
    fun getFullDateString(date: String?): String? {
        if (date == null) return null
        val dateFormat = SimpleDateFormat(YEAR_MONTH_DAY_DASH_FORMAT, Locale.getDefault())
        val dateOrigin = dateFormat.parse(date)
        val d = SimpleDateFormat(DAY_FULL_FORMAT, Locale.getDefault())
        return if (dateOrigin != null) {
            d.format(dateOrigin)
        } else null
    }
}