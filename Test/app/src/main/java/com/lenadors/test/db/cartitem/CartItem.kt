package com.lenadors.test.db.cartitem

data class CartItem(
    var id: Int? = null,
    var name: String? = null,
    var price: Double? = null,
    var tax: Double? = null,
    var discount: Double? = null,
    var quantity: Int? = null,
    var total: Double? = null,
)