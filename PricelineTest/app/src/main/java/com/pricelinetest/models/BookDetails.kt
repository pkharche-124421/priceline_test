package com.pricelinetest.models

import com.google.gson.annotations.SerializedName


data class BookDetails(
    @SerializedName("title") var title: String,
    @SerializedName("description") var description: String,
    @SerializedName("author") var author: String,
    @SerializedName("price") var price: Float
)
