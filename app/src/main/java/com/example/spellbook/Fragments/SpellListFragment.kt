package com.example.spellbook.Fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.*

import com.example.spellbook.R
import com.example.spellbook.domain.*
import com.example.spellbook.ui.SpellViewmodel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_spell_list.*
import java.lang.Class
import android.support.v4.view.MenuItemCompat.getActionView
import android.support.v4.view.MenuItemCompat.getActionView
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import android.widget.ArrayAdapter
import com.example.spellbook.ui.SpellbookViewModel
import kotlinx.android.synthetic.main.toolbar.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SpellListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class SpellListFragment : Fragment() {

    private lateinit var viewModel: SpellViewmodel

    private lateinit var spinnerValue: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(activity!!).get(SpellViewmodel::class.java)
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spell_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        viewModel.getSpells().observe(this, Observer {
            spell_list.adapter = SpellRecyclerViewAdapter(this, it!!.sortedBy { spell -> spell.level })
        })

        spell_list.layoutManager= LinearLayoutManager(activity)
    }

    fun showDetail(spell: Spell){
        val detailFragment = SpellDetailFragment()
        this.fragmentManager!!.beginTransaction()
            .replace(R.id.list_frame, detailFragment)
            .addToBackStack(null)
            .commit()
        detailFragment.addObject(spell)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        inflater!!.inflate(R.menu.listfragmentmenu, menu)

        //get query from searchview and flter list
        var search=menu!!.findItem(R.id.action_search)
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

                        viewModel.getSpells().observe(fragment, Observer {
                            var filteredsSpells=it!!.filter{spell ->
                                spell.name.toLowerCase().contains(searchQuery)
                            } as MutableList

                            if(spinnerValue != null ){
                                if(spinnerValue.toLowerCase()!="all classes"){
                                    var filteredSpellsClass= filteredsSpells.filter {spell ->
                                        spell.tags.contains(spinnerValue.toLowerCase())
                                    }

                                    spell_list.adapter=SpellRecyclerViewAdapter(fragment, filteredSpellsClass.sortedBy { spell -> spell.level })
                                }else{
                                    spell_list.adapter=SpellRecyclerViewAdapter(fragment, filteredsSpells.sortedBy { spell -> spell.level })
                                }
                            }else{
                                spell_list.adapter=SpellRecyclerViewAdapter(fragment, filteredsSpells.sortedBy { spell -> spell.level })
                            }
                        })
                    }
                    return true
                }

            })
        }

        //set items in spinner
        val item = menu.findItem(R.id.class_spinner)
        val spinner = item.actionView as Spinner

        val adapter = ArrayAdapter.createFromResource(activity, R.array.classes, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter=adapter

        spinner.onItemSelectedListener= object : OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedClass=resources.getStringArray(R.array.classes)[position].toLowerCase()
                val searchView=search.actionView as SearchView

                fragment.spinnerValue=selectedClass

                if(selectedClass != "all classes"){
                    viewModel.getSpells().observe(fragment, Observer {
                        //filter spells by selected class
                        var filteredsSpells=it!!.filter{spell ->
                            spell.tags.contains(selectedClass)
                        }
                        //then filter by search query in search bar
                        var filterbysearch=filteredsSpells.filter {spell ->
                            spell.name.contains(searchView.query)
                        }

                        spell_list.adapter=SpellRecyclerViewAdapter(fragment, filterbysearch.sortedBy { spell -> spell.level })
                    })
                }else{
                    viewModel.getSpells().observe(fragment, Observer {
                        //all classes is selected, so no class filtering required, only by search query
                        val searchresult=it!!.filter {spell ->
                            spell.name.contains(searchView.query)
                        }

                        spell_list.adapter = SpellRecyclerViewAdapter(fragment, searchresult.sortedBy { spell -> spell.level })
                    })
                }
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }
}
