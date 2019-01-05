package com.example.spellbook.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * [Components] represents the components needed to cast a spell
 *
 * @param material: tells if material components are needed
 * @param raw: show the components as string example: V, S, M (a tiny bell and a piece of fine silver wire)
 * @param somatic: tells if somatic components are needed
 * @param verbal: tells if verbal components are needed
 */
@Parcelize
class Components(@field:Json(name = "material")val material: Boolean,
                 @field:Json(name = "raw")val raw: String,
                 @field:Json(name = "somatic")val somatic: Boolean,
                 @field:Json(name = "verbal")val verbal: Boolean) : Parcelable