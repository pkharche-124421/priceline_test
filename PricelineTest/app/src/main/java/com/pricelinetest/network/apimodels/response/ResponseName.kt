package com.pricelinetest.network.apimodels.response

import com.google.gson.annotations.SerializedName
import com.pricelinetest.models.Name

data class ResponseName(
    @SerializedName("results") var nameList: List<Name>
)
