package com.example.financei.ui.adapters

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.financei.R
import com.example.financei.database.models.Count
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CountListAdapter : ListAdapter<Count, CountListAdapter.CountViewHolder>(CountComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountViewHolder {
        return  CountViewHolder.create(parent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CountViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.category, current.price, current.date)
    }

    class  CountViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val countCategory: TextView = itemView.findViewById(R.id.count_category)
        private val countPrice: TextView = itemView.findViewById(R.id.count_price)
        private val countDate: TextView = itemView.findViewById(R.id.count_date)

        @SuppressLint("SetTextI18n")
        fun bind(category: String?, price: Double, date: String){
            countCategory.text = category
            countPrice.text = "R$$price"
            countDate.text = date
        }

        companion object{
            fun create(parent: ViewGroup):  CountViewHolder{
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.count_item, parent, false)
                return CountViewHolder(view)
            }
        }
    }
    class CountComparator : DiffUtil.ItemCallback<Count>(){
        override fun areItemsTheSame(oldItem: Count, newItem: Count): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Count, newItem: Count): Boolean {
            return oldItem.category == newItem.category && oldItem.price == newItem.price
        }

    }

}