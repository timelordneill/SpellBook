package com.example.spellbook.domain

class Spell(val name: String, val level: Int, val shool: String, val source: String, val page: Int, val entries: List<String>,
            val entriesHigherLevel: List<String>,val time:List<Time>, val range: Range, val components: Components,
            val classes: Classes, val duration: Duration, val meta: Meta, val scalingeffects: Boolean) {
}