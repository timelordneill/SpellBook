package com.example.spellbook.domain

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.spellbook.Activities.MainActivity
import com.example.spellbook.Fragments.SpellListFragment
import com.example.spellbook.R
import kotlinx.android.synthetic.main.spell_list_content.view.*

class SimpleItemRecyclerViewAdapter(private val parentActivity: SpellListFragment,
                                    private val spells: List<Spell>) :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->

            val item = v.tag as Spell

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.spell_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spell = spells[position]
        holder.name.text = spell.name
        holder.level.text=spell.level.toString()
        holder.school.text=spell.shool

        with(holder.itemView) {
            tag = spell // Save the comic represented by this view
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = spells.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.text_name
        val level: TextView = view.text_level
        val school: TextView = view.text_school
    }
}