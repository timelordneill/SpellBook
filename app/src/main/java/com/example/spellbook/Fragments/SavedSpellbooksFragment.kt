package com.example.spellbook.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import com.example.spellbook.ui.SpellbookViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_saved_spellbooks.*

class SavedSpellbooksFragment : Fragment() {

    private lateinit var  spellbookViewmodel: SpellbookViewModel
    private lateinit var spellViewmodel: SpellViewmodel

    private var fullList=this.full_list

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        spellbookViewmodel = ViewModelProviders.of(activity!!).get(SpellbookViewModel::class.java)
        spellViewmodel = ViewModelProviders.of(activity!!).get(SpellViewmodel::class.java)
        return inflater.inflate(R.layout.fragment_saved_spellbooks, container, false)
    }

    override fun onStart() {
        super.onStart()

        val fullList=this.full_list
        val addBook=this.add_spellbook
        fullList.setOnClickListener{
            val listFragment = SpellListFragment()
            this.fragmentManager!!.beginTransaction()
                .replace(R.id.list_frame, listFragment)
                .addToBackStack(null)
                .commit()

            val drawer=activity!!.drawer_layout
            drawer.closeDrawers()
        }
        addBook.setOnClickListener{
            val addSpellbookFragment = AddSpellbookFragment()
            this.fragmentManager!!.beginTransaction()
                .replace(R.id.list_frame, addSpellbookFragment)
                .addToBackStack(null)
                .commit()

            val drawer=activity!!.drawer_layout
            drawer.closeDrawers()
        }

        spellbookViewmodel.allSpellbooks.observe(this, Observer {
                val converter=DatabaseSpellbookConverter(activity)
                val spellbooks=converter.fromDatabaseSpellbook(it!! as MutableList<DatabaseSpellbook>)
                spellbook_list.adapter = SpellbookRecyclerViewAdapter(this, spellbooks)
                spellbook_list.layoutManager= LinearLayoutManager(activity)
        })
    }

    fun showSavedSpellbook(spellbook: Spellbook){
        val spellbookFragment = SpellbookFragment()
        this.fragmentManager!!.beginTransaction()
            .replace(R.id.list_frame, spellbookFragment)
            .addToBackStack(null)
            .commit()
        spellbookFragment.addObject(spellbook)
    }
}
