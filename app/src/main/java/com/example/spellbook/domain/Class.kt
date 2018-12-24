package com.example.spellbook.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Class(@field:Json(name = "name")val name: String,
            @field:Json(name = "source")val source: String) : Parcelable {
}