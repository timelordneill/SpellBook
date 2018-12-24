package com.example.spellbook.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Time(@field:Json(name = "number")val number: Int,
           @field:Json(name = "unit")val unit: String) : Parcelable