package com.example.spellbook.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Distance(@field:Json(name = "type")val type: String,
               @field:Json(name = "amount")val amount: Int) : Parcelable {
}