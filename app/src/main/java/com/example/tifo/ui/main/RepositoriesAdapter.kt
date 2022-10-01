package com.example.tifo.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tifo.R
import com.example.tifo.data.Repository

class RepositoriesAdapter(private val repositories: List<Repository>) : RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

    var onItemCLick : ((Repository) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.repositories_card, parent, false)

        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val repositories = repositories[position]

        holder.name.text = repositories.full_name
        holder.technology.text = repositories.language
        holder.description.text = repositories.description
        holder.ratings.text = repositories.stargazers_count

    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    inner class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val technology: TextView = itemView.findViewById(R.id.techno)
        val description: TextView = itemView.findViewById(R.id.description)
        val ratings: TextView = itemView.findViewById(R.id.ratings)

        init {
            itemView.setOnClickListener {
                onItemCLick?.invoke(repositories[adapterPosition])
            }
        }
    }
}