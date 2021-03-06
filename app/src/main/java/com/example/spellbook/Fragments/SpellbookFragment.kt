package com.example.spellbook.Fragments

import android.app.AlertDialog
import android.os.Bundle
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*

import com.example.spellbook.R
import com.example.spellbook.domain.*
import com.example.spellbook.domain.RecyclerViewAdapters.SpellbookSpellsRecyclerViewAdapter
import com.example.spellbook.ui.SpellbookViewModel
import com.example.spellbook.utils.twoPane
import kotlinx.android.synthetic.main.fragment_spell_list.*

/**
 * [SpellbookFragment] shows spells belonging to a spellbook
 */
class SpellbookFragment : android.support.v4.app.Fragment() {

    private lateinit var spellbook: Spellbook
    private lateinit var  spellbookViewmodel: SpellbookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        spellbookViewmodel = ViewModelProviders.of(activity!!).get(SpellbookViewModel::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spellbook, container, false)
    }

    /**
     * sets adapter
     */
    override fun onStart() {
        super.onStart()

        spell_list.adapter = SpellbookSpellsRecyclerViewAdapter(this, spellbook.spells!!.sortedBy { spell -> spell.level })
        spell_list.layoutManager= LinearLayoutManager(activity)
    }

    /**
     * Adds [Spellbook] to fragment
     */
    fun addObject(item: Spellbook){
        this.spellbook = item
    }

    /**
     * shows detailscreen of [Spell]
     */
    fun showDetail(spell: Spell){
        val detailFragment = SpellDetailFragment()
        if(twoPane){
            this.fragmentManager!!.beginTransaction()
                .replace(R.id.detail_frame, detailFragment)
                .addToBackStack(null)
                .commit()
            detailFragment.addObject(spell)
        }else{
            this.fragmentManager!!.beginTransaction()
                .replace(R.id.list_frame, detailFragment)
                .addToBackStack(null)
                .commit()
            detailFragment.addObject(spell)
        }
    }

    /**
     * deletes [Spell] from [spellbook]
     */
    fun deleteSpell(spell: Spell){
        val alert= AlertDialog.Builder(context)
        alert.setMessage("Are you sure you want to delete this spell")
        alert.setCancelable(true)

        alert.setPositiveButton("Yes"){dialog, which ->
            spellbook.spells!!.remove(spell)
            val converter=DatabaseSpellbookConverter(activity)
            spellbookViewmodel.update(converter.toDatabaseSpellbook(spellbook))

            spell_list.adapter = SpellbookSpellsRecyclerViewAdapter(this, spellbook.spells!!.sortedBy { spell -> spell.level })
            spell_list.layoutManager= LinearLayoutManager(activity)

            dialog.dismiss()
        }

        alert.setNegativeButton("No"){dialog, which ->
            dialog.dismiss()
        }

        val dialog=alert.create()
        dialog.show()
    }

    /**
     * creates teh actionbar and handels searching
     */
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.spellbookfragmentmenu, menu)

        val search=menu!!.findItem(R.id.spellbook_search)
        val fragment=this

        if(search != null){
            val searchView=search.actionView as SearchView
            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    if(p0!!.isNotEmpty()){
                        val searchQuery = p0.toLowerCase()

                        val filteredSpells=spellbook.spells!!.filter {
                            it.name.toLowerCase().contains(searchQuery)
                        }.sortedBy { spell -> spell.level }

                        spell_list.adapter = SpellbookSpellsRecyclerViewAdapter(fragment, filteredSpells.sortedBy { spell -> spell.level })
                        spell_list.layoutManager= LinearLayoutManager(activity)
                    }
                    return true
                }

            })
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Sets click listener to edit button
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.spellbook_edit -> {
                showEdit()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /**
     * takes user to [SpellbookEditFragment]
     */
    fun showEdit(){
        val editFragment = SpellbookEditFragment()
        this.fragmentManager!!.beginTransaction()
            .replace(R.id.list_frame, editFragment)
            .addToBackStack(null)
            .commit()
        editFragment.addObject(spellbook)
    }
}
