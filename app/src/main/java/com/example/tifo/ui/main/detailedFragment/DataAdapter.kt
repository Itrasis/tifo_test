package com.example.tifo.ui.main.detailedFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.tifo.R
import com.example.tifo.data.DataModel

class DataAdapter(private val adapterData: List<DataModel>, private val context : Context) : RecyclerView.Adapter<DataAdapter.DataAdapterViewHolder>() {


    companion object {
        private const val TYPE_USER = 0
        private const val TYPE_BRANCH = 1
        private const val TYPE_HEADER = 2
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataAdapterViewHolder {

        val layout = when (viewType) {
            TYPE_BRANCH -> R.layout.branch_card
            TYPE_USER -> R.layout.user_card
            TYPE_HEADER -> R.layout.header_card
            else -> throw IllegalArgumentException("Invalid type")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)

        return DataAdapterViewHolder(view)
    }




    override fun getItemViewType(position: Int): Int {

        return when (adapterData[position]) {
            is DataModel.User  -> TYPE_USER
            is DataModel.Branch-> TYPE_BRANCH
            is DataModel.Header -> TYPE_HEADER
        }
    }


    override fun onBindViewHolder(holder: DataAdapterViewHolder, position: Int) {

        holder.bind(adapterData[position])
    }


    override fun getItemCount(): Int {
        return adapterData.size
    }



    inner class DataAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun bindBranch(item: DataModel.Branch) {
            itemView.findViewById<TextView>(R.id.branch).text = item.name
        }
        private fun bindUser(item: DataModel.User) {
            itemView.findViewById<TextView>(R.id.contributor).text = item.login
            Glide.with(context).load(item.avatar_url).diskCacheStrategy(DiskCacheStrategy.ALL).into(itemView.findViewById<ImageView>(R.id.avatar));

        }

        private fun bindHeader(item : DataModel.Header) {
            itemView.findViewById<TextView>(R.id.header_title).text = item.title
        }

        fun bind(dataModel: DataModel) {
            when (dataModel) {
                is DataModel.User -> bindUser(dataModel)
                is DataModel.Branch -> bindBranch(dataModel)
                is DataModel.Header -> bindHeader(dataModel)
            }
        }
    }


}