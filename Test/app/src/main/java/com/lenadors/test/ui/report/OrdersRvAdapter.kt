package com.lenadors.test.ui.report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lenadors.test.R
import com.lenadors.test.databinding.OrderItemBinding
import com.lenadors.test.db.product.OrderEntity

class OrdersRvAdapter : RecyclerView.Adapter<OrdersRvAdapterVH>() {

    private var orderList: List<OrderEntity> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersRvAdapterVH {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersRvAdapterVH(binding.root)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: OrdersRvAdapterVH, position: Int) {
        val data = orderList[position]

        val backgroundColor = if (position % 2 == 0) {
            ContextCompat.getColor(holder.itemView.context, R.color.darkBlueOpacity)
        } else {
            ContextCompat.getColor(holder.itemView.context, R.color.darkBlueOpacity2)
        }

        val bgStatus = if (data.status == "Paid") {
            ContextCompat.getColor(holder.itemView.context, R.color.green)
        } else {
            ContextCompat.getColor(holder.itemView.context, R.color.yellow)

        }

        holder.binding.llHeading.setBackgroundColor(backgroundColor)
        holder.binding.tvStatus.background.setTint(bgStatus)
        holder.binding.tvTransactionID.text = data.transactionId
        holder.binding.tvStatus.text = data.status
        holder.binding.tvAmount.text = String.format("%.2f", data.amount.toDouble()) + " AED"
        holder.binding.tvItems.text = data.items
        holder.binding.tvQuantity.text = data.quantity
        holder.binding.tvCreatedDate.text = data.createdDate
    }

    fun updateItems(items: List<OrderEntity>?) {
        orderList = items ?: emptyList()
        notifyDataSetChanged()
    }

    fun totalSales(): Int {
        return orderList.size
    }
}

class OrdersRvAdapterVH(view: View) : RecyclerView.ViewHolder(view) {
    var binding = OrderItemBinding.bind(view)
}
