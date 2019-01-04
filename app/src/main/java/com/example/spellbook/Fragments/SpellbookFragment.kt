package com.example.spellbook.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.spellbook.R
import com.example.spellbook.domain.Spell
import com.example.spellbook.domain.Spellbook

class SpellbookFragment : android.support.v4.app.Fragment() {

    private lateinit var spellbook: Spellbook

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spellbook, container, false)
    }

    fun addObject(item: Spellbook){
        this.spellbook = item
    }
}
