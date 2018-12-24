package com.example.spellbook.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Range(@field:Json(name = "type")val type: String,
            @field:Json(name = "distance")val distance: Distance) : Parcelable {
}