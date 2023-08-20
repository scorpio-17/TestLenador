package com.lenadors.test.repository

import androidx.lifecycle.LiveData
import com.lenadors.test.db.product.ProductDao
import com.lenadors.test.db.product.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    suspend fun insertProduct(product: ProductEntity) {
        productDao.saveProduct(product)
    }

    fun getAllProducts(): LiveData<List<ProductEntity>> {
        return productDao.getAllProducts()
    }
}