package com.lenadors.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lenadors.test.db.product.OrderEntity
import com.lenadors.test.db.product.ProductEntity
import com.lenadors.test.repository.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveOrderViewModel @Inject constructor(private var orderRepository: OrderRepository) :
    ViewModel() {

    fun insertOrder(order: OrderEntity) {
        viewModelScope.launch {
            orderRepository.insertProduct(order)
        }
    }
    val allOrder: LiveData<List<OrderEntity>> = orderRepository.getAllOrder()

}