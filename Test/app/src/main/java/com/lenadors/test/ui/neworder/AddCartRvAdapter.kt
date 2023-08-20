package com.lenadors.test.ui.neworder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lenadors.test.R
import com.lenadors.test.databinding.AddItemBinding
import com.lenadors.test.databinding.ProductItemBinding
import com.lenadors.test.db.cartitem.CartItem
import com.lenadors.test.db.product.ProductEntity

class AddCartRvAdapter(private val onItemRemoveClicked: (product: CartItem) -> Unit) :
    RecyclerView.Adapter<AddCartRvVH>() {

    private var addCartProductList: List<CartItem> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddCartRvVH {
        val binding = AddItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddCartRvVH(binding.root)
    }

    override fun onBindViewHolder(holder: AddCartRvVH, position: Int) {
        val data = addCartProductList[position]

        val backgroundColor = if (position % 2 == 0) {
            ContextCompat.getColor(holder.itemView.context, R.color.darkBlueOpacity)
        } else {
            ContextCompat.getColor(holder.itemView.context, R.color.darkBlueOpacity2)
        }

        holder.binding.llHeading.setBackgroundColor(backgroundColor)

        holder.binding.tvPrice.text = data.price?.toString()
        holder.binding.tvName.text = data.name
        holder.binding.tvTaxH.text = data.tax.toString()
        holder.binding.tvDiscountH.text = data.discount.toString()
        holder.binding.tvQuantityTitleH.text = data.quantity.toString()
//        val total = data.total?.times(data.quantity!!)
        holder.binding.tvTotal.text = String.format("%.2f", data.total)

        holder.binding.ivRemove.setOnClickListener {
            onItemRemoveClicked(data)
        }

    }

    override fun getItemCount(): Int {
        return addCartProductList.size
    }

    fun addItems(items: List<CartItem>?) {
        addCartProductList = items ?: emptyList()
        notifyDataSetChanged()
    }

    fun updateQuantity(item: CartItem, quantity: Int) {
        item.quantity = quantity
        item.total = item.quantity!! * item.price!!
        notifyDataSetChanged()
    }

    fun removeItem(id: Int): Int {
        if (id != -1) {
            val mutableCartList: MutableList<CartItem> = addCartProductList.toMutableList()
            val indexToRemove = mutableCartList.indexOfFirst { it.id == id }
            if (indexToRemove != -1) {
                mutableCartList.removeAt(indexToRemove)
            }
            addCartProductList = mutableCartList.toList()
            notifyDataSetChanged()
            return indexToRemove
        }
        return -1
    }

    fun totalItems(): Int {
        return addCartProductList.size
    }

    fun totalItemQuantity(): Int {
        var totalQuantity = 0
        addCartProductList.forEach { totalQuantity += it.quantity ?: 0 }
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

class AddCartRvVH(view: View) : RecyclerView.ViewHolder(view) {
    var binding: AddItemBinding = AddItemBinding.bind(view)
}
