package com.lenadors.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.lenadors.test.db.product.ProductEntity
import com.lenadors.test.viewmodel.ProductViewModel
import com.lenadors.test.viewmodel.SaveOrderViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var productViewModel: ProductViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

//        productViewModel?.insertProduct(ProductEntity(name = "Product A", price = "3.2"))
//        productViewModel?.insertProduct(ProductEntity(name = "Product B", price = "34.2"))
//        productViewModel?.insertProduct(ProductEntity(name = "Product C", price = "13.2"))
//        productViewModel?.insertProduct(ProductEntity(name = "Product D", price = "43.2"))
//        productViewModel?.insertProduct(ProductEntity(name = "Product E", price = "366.2"))
//        productViewModel?.insertProduct(ProductEntity(name = "Product F", price = "35.2"))
//        productViewModel?.insertProduct(ProductEntity(name = "Product G", price = "73.2"))
//        productViewModel?.insertProduct(ProductEntity(name = "Product G", price = "83.2"))




    }
}