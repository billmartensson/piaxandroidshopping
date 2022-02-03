package se.magictechnology.piaxshopping

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingAdapter() : RecyclerView.Adapter<ShoppingViewHolder>() {

    lateinit var shopfrag : ShoppinglistFragment


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val vh = ShoppingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.shopping_item, parent, false))
        return vh
    }

    override fun getItemCount(): Int {
        return shopfrag.shopitems.size
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {

        var currentItem = shopfrag.shopitems[position]

        holder.titleTextview.text = currentItem.shoptitle
        holder.amountTextview.text = currentItem.shopamount.toString()

        if(currentItem.shopdone == true)
        {
            holder.doneTextview.visibility = View.VISIBLE
        } else {
            holder.doneTextview.visibility = View.INVISIBLE
        }

        holder.itemView.setOnClickListener {
            shopfrag.clickrow(position)
        }
    }

}

class ShoppingViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    var titleTextview = view.findViewById<TextView>(R.id.shopitemTitleTV)
    var amountTextview = view.findViewById<TextView>(R.id.shopitemAmountTV)
    var doneTextview = view.findViewById<TextView>(R.id.shopitemDoneTV)

}