package com.pricelinetest.network.apimodels.response

import com.google.gson.annotations.SerializedName
import com.pricelinetest.models.BestSeller

data class ResponseBooksList(
    @SerializedName("results") var bestSellerList: List<BestSeller>
)
