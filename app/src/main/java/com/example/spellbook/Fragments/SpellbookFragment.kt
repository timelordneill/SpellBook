package com.example.spellbook.Fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.spellbook.R
import com.example.spellbook.domain.*
import com.example.spellbook.domain.RecyclerViewAdapters.SpellbookSpellsRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_add_spell.*
import kotlinx.android.synthetic.main.fragment_spell_list.*

class SpellbookFragment : android.support.v4.app.Fragment() {

    private lateinit var spellbook: Spellbook

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spellbook, container, false)
    }

    override fun onStart() {
        super.onStart()

        spell_list.adapter = SpellbookSpellsRecyclerViewAdapter(this, spellbook.spells)
        spell_list.layoutManager= LinearLayoutManager(activity)
    }

    fun addObject(item: Spellbook){
        this.spellbook = item
    }

    fun showDetail(spell: Spell){
        val detailFragment = SpellDetailFragment()
        this.fragmentManager!!.beginTransaction()
            .replace(R.id.list_frame, detailFragment)
            .addToBackStack(null)
            .commit()
        detailFragment.addObject(spell)
    }
}
