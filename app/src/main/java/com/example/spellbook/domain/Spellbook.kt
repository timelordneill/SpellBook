package com.example.spellbook.domain

class Spellbook(val id:Int =0,
                val name: String,
                val characterClass: List<CharacterClass>,
                val spells: MutableList<Spell>?)