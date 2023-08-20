package com.lenadors.test.ui.payment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lenadors.test.R
import com.lenadors.test.databinding.CartItemBinding
import com.lenadors.test.db.cartitem.CartItem

class CartItemRvAdapter : RecyclerView.Adapter<CartItemRvVH>() {

    private var cartProductList: List<CartItem> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemRvVH {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartItemRvVH(binding.root)
    }

    override fun getItemCount(): Int {
        return cartProductList.size
    }

    override fun onBindViewHolder(holder: CartItemRvVH, position: Int) {
        val data = cartProductList[position]

        val backgroundColor = if (position % 2 == 0) {
            ContextCompat.getColor(holder.itemView.context, R.color.darkBlueOpacity)
        } else {
            ContextCompat.getColor(holder.itemView.context, R.color.darkBlueOpacity2)
        }

        holder.binding.llHeading.setBackgroundColor(backgroundColor)
        holder.binding.tvName.text = data.name
        holder.binding.tvQuantityTitleH.text = data.quantity.toString()
        holder.binding.tvTotal.text = String.format("%.2f", data.total) + " AED"
    }

    fun addItems(items: List<CartItem>?) {
        cartProductList = items ?: emptyList()
        notifyDataSetChanged()
    }

    fun totalItems(): Int {
        return cartProductList.size
    }

    fun totalItemQuantity(): Int {
        var totalQuantity = 0
        cartProductList.forEach { totalQuantity += it.quantity ?: 0 }
        return totalQuantity
    }

    fun totalAmount(items: List<CartItem>?): String {
        var totalAmount = 0.0
        items?.forEach {
            totalAmount += it.total ?: 0.0
        }
        return String.format("%.2f", totalAmount)
    }

}

class CartItemRvVH(view: View) : RecyclerView.ViewHolder(view) {
    var binding: CartItemBinding = CartItemBinding.bind(view)
}
