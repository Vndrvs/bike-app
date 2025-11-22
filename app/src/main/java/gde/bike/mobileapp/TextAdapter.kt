package gde.bike.mobileapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gde.bike.mobileapp.databinding.RawTextBinding

class TextAdapter(
    val items: List<Bike>,
    val onItemClick: (Bike) -> Unit
    ): RecyclerView.Adapter<TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val binding = RawTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val item = items[position]
        holder.binding.brand.text = item.brand
        holder.binding.model.text = item.model
        holder.binding.price.text = item.price.toString()
        holder.binding.isAvailable.text = item.availability

        holder.binding.root.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class TextViewHolder(val binding: RawTextBinding): RecyclerView.ViewHolder(binding.root) {

}