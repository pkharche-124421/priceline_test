package com.pricelinetest

import com.pricelinetest.models.Name
import java.text.SimpleDateFormat
import java.util.*

class DateComparator {
    companion object : Comparator<Name> {

        override fun compare(name1: Name, name2: Name): Int {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val a: Date = sdf.parse(name1.newestPublishedDate)
            val b: Date = sdf.parse(name2.newestPublishedDate)
            return a.time.compareTo(b.time)
        }
    }
}