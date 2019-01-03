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

class SpellRecyclerViewAdapter(private val parentActivity: SpellListFragment,
                               private val spells: List<Spell>) :
    RecyclerView.Adapter<SpellRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    /**
     * initializes the onclick listener that takes the user to the detail screen
     */
    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Spell
            parentActivity.showDetail(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.spell_list_content, parent, false)
        return ViewHolder(view)
    }

    /**
     * fills the items in the list
     * Puts Cantrip in stead of lvl 0 spell
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spell = spells[position]
        holder.name.text = spell.name

        if(spell.level=="0"){
            holder.level.text="Cantrip"
        }else{
            holder.level.text=spell.level
        }
        holder.school.text=spell.school

        // Save the spell represented by this view
        when {
            spell.school=="abjuration" -> holder.schoolImage.setImageResource(R.drawable.abjuration)
            spell.school=="conjuration" -> holder.schoolImage.setImageResource(R.drawable.conjuration)
            spell.school=="divination" -> holder.schoolImage.setImageResource(R.drawable.divination)
            spell.school=="enchantment" -> holder.schoolImage.setImageResource(R.drawable.enchantment)
            spell.school=="evocation" -> holder.schoolImage.setImageResource(R.drawable.evocation)
            spell.school=="illusion" -> holder.schoolImage.setImageResource(R.drawable.illusion)
            spell.school=="necromancy" -> holder.schoolImage.setImageResource(R.drawable.necromancy)
            spell.school=="transmutation" -> holder.schoolImage.setImageResource(R.drawable.transmutation)
        }

        with(holder.itemView) {
            tag = spell // Save the spell represented by this view
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = spells.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.text_name
        val level: TextView = view.text_level
        val school: TextView = view.text_school
        val schoolImage: de.hdodenhof.circleimageview.CircleImageView=view.school_image
    }
}