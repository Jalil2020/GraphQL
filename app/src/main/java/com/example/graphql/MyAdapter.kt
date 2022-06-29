package com.example.graphql

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private var list = ArrayList<LaunchListQuery.Launch>()

    var onItemClicked: ((LaunchListQuery.Launch) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClicked?.invoke(list[adapterPosition])
            }

        }

        fun bind() {
            val data = list[adapterPosition]
            itemView.findViewById<TextView>(R.id.item_title).text = data.site
            itemView.findViewById<TextView>(R.id.item_text).text = data.mission?.name
            itemView.findViewById<ImageView>(R.id.item_image).load(data.mission?.missionPatch)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind()
    override fun getItemCount(): Int = list.size

    fun submitList(l: ArrayList<LaunchListQuery.Launch>) {
        list = l
        notifyDataSetChanged()
    }
}