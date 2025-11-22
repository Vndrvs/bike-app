package gde.bike.mobileapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import gde.bike.mobileapp.databinding.RawTextBinding

// Recyclerview dokumentáció: https://developer.android.com/develop/ui/views/layout/recyclerview
class TextAdapter(
    // lista, amit a RecyclerView rajzol a kijelzőre
    val items: List<Bike>,
    // a hívó Activity adja meg, mi történjen egy elemre nyomva
    val onItemClick: (Bike) -> Unit
    ): RecyclerView.Adapter<TextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextViewHolder {
        val binding = RawTextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextViewHolder, position: Int) {
        val item = items[position] // aktuális lista item
        // record mezőinek feltöltése beérkező adatokkal
        holder.binding.brand.text = item.brand
        holder.binding.model.text = item.model
        holder.binding.price.text = item.price.toString()
        holder.binding.isAvailable.text = item.availability

        // kattintáskor visszaadjuk az adott bike object-et
        holder.binding.root.setOnClickListener {
            onItemClick(item)
        }
    }
    // visszaadja a lista elemeinek számát a Recyclerview-nek
    override fun getItemCount(): Int {
        return items.size
    }
}
// ViewHolder: egyetlen sorhoz tartozó binding + root nézet
class TextViewHolder(val binding: RawTextBinding): RecyclerView.ViewHolder(binding.root) {

}