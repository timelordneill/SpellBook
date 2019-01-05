package com.example.spellbook.domain

/**
 * [CharacterClass] represents the classes a character can have
 *
 * @param name: Represents the name of the class as a [Classes] enum
 * @param level: Represents the level the character has in this class
 */
class CharacterClass (val name: Classes,
                      var level: Int)