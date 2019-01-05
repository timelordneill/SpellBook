package com.example.spellbook.domain.RecyclerViewAdapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.spellbook.Fragments.SavedSpellbooksFragment
import com.example.spellbook.Fragments.SpellbookEditFragment
import com.example.spellbook.R
import com.example.spellbook.domain.CharacterClass
import com.example.spellbook.domain.Classes
import com.example.spellbook.domain.Spellbook
import kotlinx.android.synthetic.main.spellbook_list_content.view.*

/**
 * [SpellbookRecyclerViewAdapter] is used in [SavedSpellbooksFragment] to fill the recyclerview
 *
 * @param parentActivity: parent of the recyclerview that uses this adapter
 * @param spellbooks: [Spellbook] that are shown in the list
 */
class SpellbookRecyclerViewAdapter(private val parentActivity: SavedSpellbooksFragment,
                               private val spellbooks: List<Spellbook>) :
    RecyclerView.Adapter<SpellbookRecyclerViewAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener
    private val onLongClickListener: View.OnLongClickListener

    /**
     * initializes the onclick listener that takes the user to the spellbook screen and longclick that deletes it
     */
    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Spellbook
            parentActivity.showSavedSpellbook(item)
        }

        onLongClickListener=View.OnLongClickListener { v ->
            val item = v.tag as Spellbook
            parentActivity.deleteSpellbook(item)
            true
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

        when {
            spellbook.characterClass[0].name== Classes.Bard -> holder.classImage.setImageResource(R.drawable.bard)
            spellbook.characterClass[0].name== Classes.Sorcerer -> holder.classImage.setImageResource(R.drawable.sorcerer)
            spellbook.characterClass[0].name== Classes.Cleric -> holder.classImage.setImageResource(R.drawable.cleric)
            spellbook.characterClass[0].name== Classes.Druid -> holder.classImage.setImageResource(R.drawable.druid)
            spellbook.characterClass[0].name== Classes.Ranger -> holder.classImage.setImageResource(R.drawable.ranger)
            spellbook.characterClass[0].name== Classes.Paladin -> holder.classImage.setImageResource(R.drawable.paladin)
            spellbook.characterClass[0].name== Classes.Warlock -> holder.classImage.setImageResource(R.drawable.warlock)
            spellbook.characterClass[0].name== Classes.Wizard -> holder.classImage.setImageResource(R.drawable.wizard)
        }

        with(holder.itemView) {
            tag = spellbook // Save the spell represented by this view
            setOnClickListener(onClickListener)
            setOnLongClickListener(onLongClickListener)
        }
    }

    override fun getItemCount() = spellbooks.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.text_charactername
        val classImage: de.hdodenhof.circleimageview.CircleImageView=view.class_image
    }
}