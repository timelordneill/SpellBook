package com.example.spellbook.Fragments

import android.arch.lifecycle.Observer
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.spellbook.R
import com.example.spellbook.domain.*
import com.example.spellbook.ui.SpellViewmodel
import kotlinx.android.synthetic.main.fragment_saved_spellbooks.*

class SavedSpellbooksFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_spellbooks, container, false)
    }

    override fun onStart() {
        super.onStart()

        val spellbooks= arrayListOf<Spellbook>(Spellbook("Frank", arrayListOf(CharacterClass(Classes.Bard, 5)), null), Spellbook("Donk", arrayListOf(CharacterClass(Classes.Sorcerer, 5)), null))

        spellbook_list.adapter = SpellbookRecyclerViewAdapter(this, spellbooks)

        spellbook_list.layoutManager= LinearLayoutManager(activity)
    }
}
