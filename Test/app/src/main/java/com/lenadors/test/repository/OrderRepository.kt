package com.lenadors.test.repository

import androidx.lifecycle.LiveData
import androidx.room.Index
import com.lenadors.test.db.product.OrderDao
import com.lenadors.test.db.product.OrderEntity
import com.lenadors.test.db.product.ProductDao
import com.lenadors.test.db.product.ProductEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(private val orderDao: OrderDao) {
    suspend fun insertProduct(order: OrderEntity) {
        orderDao.addOrder(order)
    }

    fun getAllOrder(): LiveData<List<OrderEntity>> {
        return orderDao.getAllOrder()
    }
}