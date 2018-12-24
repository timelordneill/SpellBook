package com.example.spellbook.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Duration(@field:Json(name = "concentration")val concentration: Boolean,
               @field:Json(name = "duration")val duration: Time?,
               @field:Json(name = "type") val type: String?) : Parcelable {
}