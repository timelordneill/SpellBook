package com.example.spellbook.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Classes(@field:Json(name = "fromClassList")val fromClassList: List<Class>,
              @field:Json(name = "fromSubclass")val fromSubclass: List<Class>) : Parcelable {
}