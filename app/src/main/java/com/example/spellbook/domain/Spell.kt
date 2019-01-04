package com.example.spellbook.domain

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.example.spellbook.injection.component.ViewModelComponent
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
class Spell (@field:Json(name = "name")val name: String,
             @field:Json(name = "description")val description: String,
             @field:Json(name = "range")val range: String,
             @field:Json(name = "components")val components: Components,
             @field:Json(name = "ritual")val ritual: Boolean,
             @field:Json(name = "duration")val duration: String,
             @field:Json(name = "concentration")val concentration: Boolean,
             @field:Json(name = "casting_time")val casting_time: String,
             @field:Json(name = "level")val level: String,
             @field:Json(name = "school")val school: String,
             @field:Json(name = "classes")val classes: List<String>,
             @field:Json(name = "tags")val tags: List<String>,
             @field:Json(name = "type")val type: String) : Parcelable

