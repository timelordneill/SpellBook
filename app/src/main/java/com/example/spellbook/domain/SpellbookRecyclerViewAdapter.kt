package com.example.spellbook.domain

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.spellbook.Activities.MainActivity
import com.example.spellbook.Fragments.SavedSpellbooksFragment
import com.example.spellbook.Fragments.SpellListFragment
import com.example.spellbook.R
import kotlinx.android.synthetic.main.spell_list_content.view.*
import kotlinx.android.synthetic.main.spellbook_list_content.view.*

class SpellbookRecyclerViewAdapter(private val parentActivity: SavedSpellbooksFragment,
                               private val spellbooks: List<Spellbook>) :
    RecyclerView.Adapter<SpellbookRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    /**
     * initializes the onclick listener that takes the user to the detail screen
     */
    init {
        onClickListener = View.OnClickListener { v ->
//            val item = v.tag as Spell
//            parentActivity.showDetail(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.spellbook_list_content, parent, false)
        return ViewHolder(view)
    }

    /**
     * fills the items in the list
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spellbook = spellbooks[position]
        holder.name.text = spellbook.name
        var charaterclasses=""
        spellbook.characterClass.forEach {
            charaterclasses=charaterclasses+it.name.className+" "+it.level.toString()+" ,"
        }
        holder.characterClass.text=charaterclasses
    }

    override fun getItemCount() = spellbooks.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.text_charactername
        val characterClass: TextView = view.text_class
    }
}