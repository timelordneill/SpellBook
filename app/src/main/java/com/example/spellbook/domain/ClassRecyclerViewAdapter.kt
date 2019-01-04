package com.example.spellbook.domain

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.spellbook.Activities.MainActivity
import com.example.spellbook.Fragments.AddSpellbookFragment
import com.example.spellbook.Fragments.SpellListFragment
import com.example.spellbook.R
import kotlinx.android.synthetic.main.class_list_content.view.*

class ClassRecyclerViewAdapter(private val parentActivity: AddSpellbookFragment,
                               private val classes: List<CharacterClass>) :
    RecyclerView.Adapter<ClassRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.class_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val characterClass = classes[position]
        holder.name.text = characterClass.name.className
        holder.level.text=characterClass.level.toString()

        when {
            characterClass.name == Classes.Bard -> holder.classImage.setImageResource(R.drawable.bard)
            characterClass.name == Classes.Sorcerer -> holder.classImage.setImageResource(R.drawable.sorcerer)
            characterClass.name == Classes.Cleric -> holder.classImage.setImageResource(R.drawable.cleric)
            characterClass.name == Classes.Druid -> holder.classImage.setImageResource(R.drawable.druid)
            characterClass.name == Classes.Ranger -> holder.classImage.setImageResource(R.drawable.ranger)
            characterClass.name == Classes.Paladin -> holder.classImage.setImageResource(R.drawable.paladin)
            characterClass.name == Classes.Warlock -> holder.classImage.setImageResource(R.drawable.warlock)
            characterClass.name == Classes.Wizard -> holder.classImage.setImageResource(R.drawable.wizard)
        }
    }

    override fun getItemCount() = classes.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.text_classname
        val level: TextView=view.text_level
        val classImage: de.hdodenhof.circleimageview.CircleImageView = view.class_image
    }

}