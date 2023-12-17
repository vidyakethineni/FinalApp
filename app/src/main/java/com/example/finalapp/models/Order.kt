package com.example.finalapp.models

/**
 * Represents an order placed by a user in the application.
 * An order includes a list of food items, delivery information, and special instructions.
 */
data class Order(
    val foodItems: List<FoodItem>? = null,
    var dateTime: String? = null,
    var restaurantName: String? = null,
    var deliveryAddress: String? = null,
    var currentLocation: LatLng? = null,
    var splInstructions: String? = ""
)