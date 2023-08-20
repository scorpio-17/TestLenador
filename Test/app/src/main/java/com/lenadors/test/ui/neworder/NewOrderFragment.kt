package com.lenadors.test.ui.neworder

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lenadors.test.BaseFragment
import com.lenadors.test.Common
import com.lenadors.test.Common.Companion.generateRandomSixDigitNumber
import com.lenadors.test.Common.Companion.getCurrentDateAndTime
import com.lenadors.test.R
import com.lenadors.test.databinding.FragmentNewOrderBinding
import com.lenadors.test.db.cartitem.CartItem
import com.lenadors.test.db.product.OrderEntity
import com.lenadors.test.db.product.ProductEntity
import com.lenadors.test.viewmodel.ProductViewModel
import com.lenadors.test.viewmodel.SaveOrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewOrderFragment : BaseFragment<FragmentNewOrderBinding>(FragmentNewOrderBinding::inflate) {

    private var productViewModel: ProductViewModel? = null
    private var viewModel: SaveOrderViewModel? = null
    private var mProductListAdapter = ProductRvAdapter { onItemClicked(it) }
    private var mAddCartRvAdapter = AddCartRvAdapter { onItemRemoveClicked(it) }
    private var isAction: Boolean = false
    private var totalAmount: Double = 0.0


    companion object {
        var addToCartProductList: MutableList<CartItem>? = mutableListOf()

    }

    private fun onItemClicked(productEntity: ProductEntity) {

        if (addToCartProductList?.size == 0) {
            addItemToCart(productEntity)
            mAddCartRvAdapter.addItems(addToCartProductList)

        } else {

            val productToUpdate = addToCartProductList?.find { it.id == productEntity.id }
            if (productToUpdate != null) {
                mAddCartRvAdapter.updateQuantity(
                    productToUpdate,
                    productToUpdate.quantity?.plus(1) ?: 0
                )
            } else {
                addItemToCart(productEntity)
                mAddCartRvAdapter.addItems(addToCartProductList)
            }
        }


        setAddToCartList()
    }

    private fun addItemToCart(productEntity: ProductEntity) {
        addToCartProductList?.add(
            CartItem(
                id = productEntity.id,
                name = productEntity.name,
                price = productEntity.price.toDouble(),
                tax = 0.0,
                discount = 0.0,
                quantity = 1,
                total = productEntity.price.toDouble()
            )
        )
    }

    private fun onItemRemoveClicked(it: CartItem) {
        val index = mAddCartRvAdapter.removeItem(it.id ?: -1)
        addToCartProductList?.removeAt(index)
        setAddToCartList()
    }

    private fun setAddToCartList() {
        if (addToCartProductList?.size != 0) {
            isAction = true
            changeActionButtonColors(requireActivity().getColor(R.color.darkBlue))
        } else {
            isAction = false
            changeActionButtonColors(requireActivity().getColor(R.color.darkBlueOpacity2))
        }
        totalAmountUI()
        totalItemsUI()
        totalItemQuantity()
        binding.rvAddItems.apply {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            adapter = mAddCartRvAdapter
            mAddCartRvAdapter.notifyDataSetChanged()
        }


    }

    private fun changeActionButtonColors(color: Int) {
        binding.rlClear.backgroundTintList =
            ColorStateList.valueOf(color)
        binding.rlPrint.backgroundTintList =
            ColorStateList.valueOf(color)
        binding.rlPayment.backgroundTintList =
            ColorStateList.valueOf(color)
        binding.rlSuspend.backgroundTintList =
            ColorStateList.valueOf(color)
        binding.rlDiscount.backgroundTintList =
            ColorStateList.valueOf(color)
    }

    private fun totalItemQuantity() {
        binding.tvQuantity.text = mAddCartRvAdapter.totalItemQuantity().toString()
    }

    private fun totalItemsUI() {
        binding.tvTotalItems.text = mAddCartRvAdapter.totalItems().toString()
    }

    private fun totalAmountUI() {
        val taxAmount = Common.calculateTaxAmount(
            mAddCartRvAdapter.totalAmount(addToCartProductList).toDouble(),
            5.00
        )
        totalAmount = mAddCartRvAdapter.totalAmount(addToCartProductList).toDouble()
            .plus(taxAmount)
        binding.tvTotalAmount.text =
            String.format("%.2f", totalAmount).toDouble().toString() + " AED"
        binding.tvSubTotal.text = String.format("%.2f", totalAmount).toDouble().toString() + " AED"
        binding.tvTax.text = String.format("%.2f", taxAmount).toDouble().toString() + " AED"
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]
        viewModel = ViewModelProvider(this)[SaveOrderViewModel::class.java]
        getData()
        setAdapter()
        binding.rlPayment.setOnClickListener {
            findNavController().navigate(R.id.action_newOrderFragment_to_paymentFragment2)
        }

        binding.rlSuspend.setOnClickListener {
            viewModel?.insertOrder(
                OrderEntity(
                    transactionId = generateRandomSixDigitNumber().toString(),
                    status = "Suspend",
                    amount = totalAmount.toString(),
                    items = mAddCartRvAdapter.totalItems().toString(),
                    quantity = mAddCartRvAdapter.totalItemQuantity().toString(),
                    createdDate = getCurrentDateAndTime()
                )
            )

            addToCartProductList?.clear()
            findNavController().navigate(R.id.selectionFragment)
        }
    }

    private fun getData() {
        productViewModel?.allProducts?.observe(requireActivity()) {
            mProductListAdapter.updateItems(it)
            mProductListAdapter?.notifyDataSetChanged()
        }
    }

    private fun setAdapter() {
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireActivity(), 3)
            adapter = mProductListAdapter
        }
    }


    override fun onResume() {
        super.onResume()
        setAddToCartList()
    }


}