package com.example.spellbook.domain

class Spellbook(val id:Int =0,
                var name: String,
                val characterClass: MutableList<CharacterClass>,
                val spells: MutableList<Spell>?)