package com.example.spellbook.domain

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

/**
 * [Spell] represents the spells a character can cast
 *
 * @param name: name of the spell
 * @param description : description of the spell
 * @param range: casting range of the spell
 * @param components: components needed to cast the spell
 * @param ritual: tells if this spell can be cast as a ritual
 * @param duration: duration of the spell
 * @param concentration: tells if concentration is needed to cast the spell
 * @param casting_time: time needed to cast the spell
 * @param level: level of the spell
 * @param school: spell school of the spell
 * @param classes: classes that are abel to cast the spell
 * @param tags: tags for easy searching
 * @param type: string with spell info
 */
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

