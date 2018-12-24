package com.example.spellbook.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize


@Parcelize
class Spell(@field:Json(name = "name")val name: String,
            @field:Json(name = "level")val level: Int,
            @field:Json(name = "shool")val shool: String,
            @field:Json(name = "source")val source: String,
            @field:Json(name = "page")val page: Int,
            @field:Json(name = "entries")val entries: List<String>,
            @field:Json(name = "entriesHigherLevel")val entriesHigherLevel: List<String>,
            @field:Json(name = "time")val time:List<Time>, val range: Range,
            @field:Json(name = "components")val components: Components,
            @field:Json(name = "classes")val classes: Classes,
            @field:Json(name = "duration")val duration: Duration,
            @field:Json(name = "meta")val meta: Meta,
            @field:Json(name = "scalingeffects")val scalingeffects: Boolean) : Parcelable
