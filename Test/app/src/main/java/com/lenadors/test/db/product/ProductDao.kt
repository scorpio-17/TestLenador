package com.lenadors.test.db.product

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    @Insert
    suspend fun saveProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): LiveData<List<ProductEntity>>

    @Delete
    suspend fun deleteProduct(productEntity: ProductEntity)
}

@Dao
interface OrderDao {
    @Insert
    suspend fun addOrder(orderEntity: OrderEntity)

    @Query("SELECT * FROM order_table")
    fun getAllOrder(): LiveData<List<OrderEntity>>

}