package com.lenadors.test.ui.payment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lenadors.test.BaseFragment
import com.lenadors.test.Common
import com.lenadors.test.Common.Companion.generateRandomSixDigitNumber
import com.lenadors.test.Common.Companion.getCurrentDateAndTime
import com.lenadors.test.R
import com.lenadors.test.databinding.FragmentPaymentBinding
import com.lenadors.test.db.product.OrderEntity
import com.lenadors.test.ui.neworder.NewOrderFragment.Companion.addToCartProductList
import com.lenadors.test.viewmodel.SaveOrderViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

@AndroidEntryPoint
class PaymentFragment : BaseFragment<FragmentPaymentBinding>(FragmentPaymentBinding::inflate) {

    private var mCartItemRvAdapter: CartItemRvAdapter? = null

    private val navController by lazy { findNavController() }

    private var isCompleted: Boolean = false

    private var viewModel: SaveOrderViewModel? = null

    private var status: Boolean = false

    private var taxAmount: Double? = 0.0
    private var totalAmount: Double? = 0.0


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this)[SaveOrderViewModel::class.java]



        setAdapter()
        updateOrderSummary()
        updateOrderSummaryDetails(
            totalAmount, 0.0, 0.0, 0.0
        )


        binding.tvCustomerID.text =
            requireActivity().getString(R.string.customer_id) + ": " + generateRandomSixDigitNumber().toString()

        binding.rlBack.setOnClickListener {
            if (!isCompleted) {
                navController.navigateUp()
            }
        }

        binding.rlCard.setOnClickListener {
            if (!isCompleted) {
                isCompleted = true
                payment(true)
            }
        }
        binding.rlCash.setOnClickListener {
            if (!isCompleted) {
                isCompleted = true
                payment(true)
            }
        }

        binding.rlSuspend.setOnClickListener {
            if (!isCompleted) {
                isCompleted = true
                payment(false)
            }
        }

        binding.rlComplete.setOnClickListener {
            if (isCompleted) {
                findNavController().navigate(R.id.selectionFragment)
            }
        }

        binding.rlFinish.setOnClickListener {
            if (isCompleted) {
                findNavController().navigate(R.id.selectionFragment)
            }
        }


    }

    private fun payment(b: Boolean) {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please wait...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        Handler(Looper.getMainLooper()).postDelayed({
            // Hide the progress dialog when loading is done
            progressDialog.dismiss()
            if (b) {
                updateOrderSummaryDetails(
                    0.0,
                    mCartItemRvAdapter?.totalAmount(addToCartProductList)?.toDouble() ?: 0.0,
                    mCartItemRvAdapter?.totalAmount(addToCartProductList)?.toDouble() ?: 0.0,
                    0.0
                )
            } else {
                updateOrderSummaryDetails(
                    mCartItemRvAdapter?.totalAmount(addToCartProductList)?.toDouble() ?: 0.0,
                    0.0, 0.0, 0.0
                )
            }
            changeActionButtonColors(
                requireActivity().getColor(R.color.darkBlue),
                requireActivity().getColor(R.color.darkBlueOpacity2)
            )
            saveOrderIntoDB(b)
        }, 5000)
    }

    private fun saveOrderIntoDB(status: Boolean) {
        var s = ""
        s = if (status) {
            "Paid"
        } else {
            "Suspend"
        }
        viewModel?.insertOrder(
            OrderEntity(
                transactionId = Common.generateRandomSixDigitNumber().toString(),
                status = s,
                amount = totalAmount.toString(),
                items = mCartItemRvAdapter?.totalItems().toString(),
                quantity = mCartItemRvAdapter?.totalItemQuantity().toString(),
                createdDate = getCurrentDateAndTime()
            )
        )

        addToCartProductList?.clear()

    }

    private fun changeActionButtonColors(active: Int, inActive: Int) {

        binding.rlCash.backgroundTintList = ColorStateList.valueOf(inActive)
        binding.rlCard.backgroundTintList = ColorStateList.valueOf(inActive)
        binding.rlVoucher.backgroundTintList = ColorStateList.valueOf(inActive)
        binding.rlBack.backgroundTintList = ColorStateList.valueOf(inActive)
        binding.rlSuspend.backgroundTintList = ColorStateList.valueOf(inActive)
        binding.rlComplete.backgroundTintList = ColorStateList.valueOf(active)
        binding.rlFinish.backgroundTintList = ColorStateList.valueOf(active)

    }


    private fun updateOrderSummaryDetails(
        outstanding: Double?, paid: Double, received: Double, change: Double
    ) {
        binding.tvOutstanding.text =
            String.format("%.2f", outstanding).toDouble().toString() + " AED"
        binding.tvPaid.text = String.format("%.2f", paid).toDouble().toString() + " AED"
        binding.tvReceived.text = String.format("%.2f", received).toDouble().toString() + " AED"
        binding.tvChange.text = String.format("%.2f", change).toDouble().toString() + " AED"
    }

    private fun updateOrderSummary() {
        taxAmount = Common.calculateTaxAmount(
            mCartItemRvAdapter?.totalAmount(addToCartProductList)?.toDouble() ?: 0.0,
            5.00
        )
        binding.tvTotalItems.text = mCartItemRvAdapter?.totalItems().toString()
        binding.tvQuantity.text = mCartItemRvAdapter?.totalItemQuantity().toString()
        totalAmount = mCartItemRvAdapter?.totalAmount(addToCartProductList)?.toDouble()
            ?.plus(taxAmount?.toDouble()!!)
        binding.tvTotalAmount.text =
            String.format("%.2f", totalAmount).toDouble().toString() + " AED"
        binding.tvSubTotal.text = String.format("%.2f", totalAmount).toDouble().toString() + " AED"
        binding.tvTax.text = String.format("%.2f", taxAmount).toDouble().toString() + " AED"

    }

    private fun setAdapter() {
        mCartItemRvAdapter = CartItemRvAdapter()
        mCartItemRvAdapter?.addItems(addToCartProductList)
        mCartItemRvAdapter?.notifyDataSetChanged()

        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            adapter = mCartItemRvAdapter
        }
    }


}