package com.lenadors.test.ui.selection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.lenadors.test.BaseFragment
import com.lenadors.test.R
import com.lenadors.test.databinding.FragmentSelectionBinding
import com.lenadors.test.ui.addproduct.AddProductFragment


class SelectionFragment :
    BaseFragment<FragmentSelectionBinding>(FragmentSelectionBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rlNewOrder.setOnClickListener {
            findNavController().navigate(R.id.newOrderFragment)
        }

        binding.rlOrder.setOnClickListener {
            findNavController().navigate(R.id.reportFragment)
        }

        binding.rlReport.setOnClickListener {
            val dialog = AddProductFragment()
            dialog.show(childFragmentManager, "")
        }

    }
}