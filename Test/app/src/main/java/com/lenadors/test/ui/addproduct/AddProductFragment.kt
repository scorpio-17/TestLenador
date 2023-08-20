package com.lenadors.test.ui.addproduct

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.lenadors.test.R
import com.lenadors.test.databinding.AddProductBinding
import com.lenadors.test.db.product.ProductEntity
import com.lenadors.test.viewmodel.ProductViewModel

class AddProductFragment : DialogFragment() {

    private var binding: AddProductBinding? = null
    private var viewModel: ProductViewModel? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddProductBinding.inflate(inflater)

        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE);


        viewModel = ViewModelProvider(requireActivity())[ProductViewModel::class.java]

        binding?.buttonOk?.setOnClickListener {
            if (binding?.etName?.text?.isNotEmpty() == true && binding?.etPrice?.text?.isNotEmpty() == true) {
                viewModel?.insertProduct(
                    ProductEntity(
                        name = binding?.etName?.text.toString(),
                        price = binding?.etPrice?.text.toString()
                    )
                )

                Toast.makeText(requireActivity(), "Item Saved", Toast.LENGTH_LONG).show()
                dismiss()
            }
        }

        return binding?.root
    }

}