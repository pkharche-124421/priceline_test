package com.pricelinetest.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Name(
    @SerializedName("display_name") var displayName: String,
    @SerializedName("list_name") var listName: String,
    @SerializedName("list_name_encoded") var listNameEncoded: String,
    @SerializedName("oldest_published_date") var oldestPublishedDate: String,
    @SerializedName("newest_published_date") var newestPublishedDate: String,
    @SerializedName("updated") var updated: String
): Parcelable {
    var isHeader: Boolean = false
}
