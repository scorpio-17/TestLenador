package com.lenadors.test.db.product

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ProductEntity::class, OrderEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun orderDao(): OrderDao
}