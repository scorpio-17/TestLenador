package com.lenadors.test.ui.report

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lenadors.test.BaseFragment
import com.lenadors.test.databinding.FragmentReportBinding
import com.lenadors.test.viewmodel.SaveOrderViewModel


class ReportFragment : BaseFragment<FragmentReportBinding>(FragmentReportBinding::inflate) {

    private var viewModel: SaveOrderViewModel? = null
    private var mOrdersRvAdapter: OrdersRvAdapter? = OrdersRvAdapter()

    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[SaveOrderViewModel::class.java]

        getData()
        setAdapter()

        binding.ivBack.setOnClickListener {
            navController.navigateUp()
        }

    }

    private fun updateUI() {
        binding.tvTotalSales.text = mOrdersRvAdapter?.totalSales().toString() + " Sales"
    }

    private fun getData() {
        viewModel?.allOrder?.observe(requireActivity()) { it ->
            mOrdersRvAdapter?.updateItems(it.sortedByDescending { it.createdDate })
            mOrdersRvAdapter?.notifyDataSetChanged()
            updateUI()
        }
    }

    private fun setAdapter() {
        binding.rvOrders.apply {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
            adapter = mOrdersRvAdapter
        }
    }

}