package com.example.spellbook.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Components(@field:Json(name = "material")val material: Boolean,
                 @field:Json(name = "raw")val raw: String,
                 @field:Json(name = "somatic")val somatic: Boolean,
                 @field:Json(name = "verbal")val verbal: Boolean) : Parcelable