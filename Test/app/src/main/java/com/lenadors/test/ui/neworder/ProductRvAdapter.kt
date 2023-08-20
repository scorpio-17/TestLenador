package com.lenadors.test.ui.neworder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lenadors.test.databinding.ProductItemBinding
import com.lenadors.test.db.product.ProductEntity

class ProductRvAdapter(private val onItemClicked: (product: ProductEntity) -> Unit) :
    RecyclerView.Adapter<ProductRvVH>() {

    private var productList: List<ProductEntity> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductRvVH {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductRvVH(binding.root)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductRvVH, position: Int) {
        val data = productList[position]
        holder.binding?.tvDetail?.text = data.name
        holder.binding?.tvPrice?.text = data.price + " AED"

        holder.itemView.setOnClickListener {
            onItemClicked(data)
        }

    }

    fun updateItems(items: List<ProductEntity>?) {
        productList = items ?: emptyList()
        notifyDataSetChanged()
    }
}

class ProductRvVH(view: View) : RecyclerView.ViewHolder(view) {
    var binding: ProductItemBinding? = ProductItemBinding.bind(view)
}
