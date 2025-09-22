package com.example.welonekai

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class AquariumListAdapter(private val onItemClicked: (Aquarium) -> Unit) : ListAdapter<Aquarium, AquariumListAdapter.AquariumViewHolder>(AquariumsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AquariumViewHolder {
        return AquariumViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: AquariumViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class AquariumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameItemView: TextView = itemView.findViewById(R.id.aquarium_name)
        private val descriptionItemView: TextView = itemView.findViewById(R.id.aquarium_description)

        fun bind(aquarium: Aquarium) {
            nameItemView.text = aquarium.name
            descriptionItemView.text = aquarium.description
        }

        companion object {
            fun create(parent: ViewGroup): AquariumViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_aquarium, parent, false)
                return AquariumViewHolder(view)
            }
        }
    }

    class AquariumsComparator : DiffUtil.ItemCallback<Aquarium>() {
        override fun areItemsTheSame(oldItem: Aquarium, newItem: Aquarium): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Aquarium, newItem: Aquarium): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
