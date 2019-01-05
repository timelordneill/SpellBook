package com.example.spellbook.domain

/**
 * [Spellbook] represents a list of spells belonging to a character
 *
 * @param name: name of the charachter
 * @param characterClass: classes this character has
 * @param spells: list of spells this character knows
 */
class Spellbook(val id:Int =0,
                var name: String,
                var characterClass: MutableList<CharacterClass>,
                var spells: MutableList<Spell>?)