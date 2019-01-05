package com.example.spellbook.domain

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * [DatabaseSpellbook] is used to locally store a [Spellbook]
 *
 * @param name: name of the character
 * @param characterClasses: string concatenated from [CharacterClass] of original [Spellbook] object. Parameters are divided by , and objects by ;
 * @param spells: string concatenated from [Spell.name] of [Spellbook.spells]
 */
@Entity(tableName = "spellbook_table")
class DatabaseSpellbook (@PrimaryKey(autoGenerate = true)
                         val id:Int,
                         val name: String="",
                         val characterClasses:String="",
                         val spells:String="")
