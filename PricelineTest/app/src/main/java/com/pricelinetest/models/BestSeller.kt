package com.pricelinetest.models

import com.google.gson.annotations.SerializedName

//results object of ResponseBooksList
data class BestSeller(
    @SerializedName("display_name") var displayName: String,
    @SerializedName("book_details") var bookDetails: List<BookDetails>
)
