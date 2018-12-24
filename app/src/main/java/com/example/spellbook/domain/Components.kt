package com.example.spellbook.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Components(@field:Json(name = "v")val v:Boolean,
                 @field:Json(name = "s")val s: Boolean,
                 @field:Json(name = "m")val m:String) : Parcelable {
}