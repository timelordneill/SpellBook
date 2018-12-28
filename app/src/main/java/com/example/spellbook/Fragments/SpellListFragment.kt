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

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SpellListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 *
 */
class SpellListFragment : Fragment() {

    private lateinit var viewModel: SpellViewmodel


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
            spell_list.adapter = SimpleItemRecyclerViewAdapter(this, it!!.sortedBy { spell -> spell.level })
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

    fun sortSpells(list: List<Spell>): List<Spell>{
        var cantrips: MutableList<Spell> = arrayListOf()

        list.forEach {
            if(it.level.toLowerCase()=="cantrip"){
                cantrips.add(it)
            }
        }

        var sortedlist=list.filter {
            it.level.toLowerCase().contains("cantrip")
        }.sortedBy {
            it.level.toInt()
        }.sortedBy {
            it.name
        }

        var sortedcantrips = cantrips.sortedBy { it.name } as MutableList<Spell>
        sortedcantrips.addAll(sortedcantrips)

        return sortedcantrips
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
                            }
                            spell_list.adapter=SimpleItemRecyclerViewAdapter(fragment, filteredsSpells.sortedBy { spell -> spell.level })
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

                if(selectedClass != "all classes"){
                    viewModel.getSpells().observe(fragment, Observer {
                        var filteredsSpells=it!!.filter{spell ->
                            spell.tags.contains(selectedClass)
                        }
                        spell_list.adapter=SimpleItemRecyclerViewAdapter(fragment, filteredsSpells.sortedBy { spell -> spell.level })
                    })
                }else{
                    viewModel.getSpells().observe(fragment, Observer {
                        spell_list.adapter = SimpleItemRecyclerViewAdapter(fragment, it!!.sortedBy { spell -> spell.level })
                    })
                }
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }
}
