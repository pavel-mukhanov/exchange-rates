package com.mukhanov.exchangerates

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mukhanov.exchangerates.model.Rate

class RatesAdapter: RecyclerView.Adapter<ViewHolder>() {
    private var items = listOf<Rate>()
    var amount: Float = 100f
        set(value) {
            field = value
            notifyItemRangeChanged(0, itemCount)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.rate_list_item, parent, false)
        return ViewHolder(root)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.rateName.text = item.name
        holder.rateValue.text = holder.itemView.context.getString(R.string.rate_format).format(amount / item.value)
    }

    fun update(newItems: List<Rate>) {
        if (newItems != items) {
            items = newItems
            notifyDataSetChanged()
        }
    }
}

class ViewHolder(root: View): RecyclerView.ViewHolder(root) {
    val rateName: TextView = root.findViewById(R.id.rate_name)
    val rateValue: TextView = root.findViewById(R.id.rate_value)
}
