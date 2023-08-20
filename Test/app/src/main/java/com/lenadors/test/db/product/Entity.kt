package com.lenadors.test.db.product

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "price") var price: String
)

@Entity(tableName = "order_table")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "transactionId") var transactionId: String,
    @ColumnInfo(name = "status") var status: String,
    @ColumnInfo(name = "amount") var amount: String,
    @ColumnInfo(name = "items") var items: String,
    @ColumnInfo(name = "quantity") var quantity: String,
    @ColumnInfo(name = "createdDate") var createdDate: String,
)