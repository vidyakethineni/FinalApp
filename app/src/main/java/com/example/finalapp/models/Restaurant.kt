package com.example.finalapp.models

import java.util.Date
import kotlin.random.Random
/**
 * Represents a geographical point with latitude and longitude coordinates.
 */
data class LatLng(val latitude: Double? = null, val longitude: Double? = null)
/**
 * Represents a food item with its name, price, maximum quantity, and initial quantity.
 */
data class FoodItem(var name: String? = null, var price: Int? = null, var maxQuantity: Int? = null,var initialQty:Int?=1)
/**
 * Represents a restaurant with its name, rating, list of food items, image URLs, and location.
 */
data class Restaurant(
    val name: String? = null,
    val rating: Float? = null,
    val foodItems: List<FoodItem>? = null,
    val imageUrls: List<String>? = null,
    val latLng: LatLng? = null
)

/**
 * Generates a list of sample orders with random food items, restaurants, and delivery addresses.
 */
fun getDummyOrders(): List<Order> {
    val foodItemsData = listOf(
        FoodItem("Drunken noodles", 15, 4),
        FoodItem("Butter chicken", 25, 4),
        FoodItem("Bento box", 20, 4),
        FoodItem("Seafood ravioli", 30, 4),
        FoodItem("Hand-pulled ramen", 18, 4),
        FoodItem("Chicken taco combo", 22, 4),
        FoodItem("California roll", 12, 4),
        FoodItem("Burger", 15, 4),
        FoodItem("Pizza", 20, 4),
        FoodItem("Fries", 10, 4)
    )

    val restaurantNames = listOf(
        "Three Amigos", "Da Vinci", "Mura", "Siam House",
        "Happy Thai", "Amrit India", "Lan Ramen", "Ami"
    )

    val latLngList = listOf(
        LatLng(39.1719, -86.5353),
        LatLng(39.1647, -86.5325),
        LatLng(39.1720, -86.5115),
        LatLng(39.1654, -86.5284),
        LatLng(39.1653, -86.5264),
        LatLng(39.1674, -86.5334),
        LatLng(39.1650, -86.5326),
        LatLng(39.1641, -86.5151)
    )

    val dummyOrders = mutableListOf<Order>()

    val currentLocation = latLngList[Random.nextInt(latLngList.size)]

    for (i in 1..5) {
        val restaurantIndex = Random.nextInt(restaurantNames.size)
         val randomFoodItems = foodItemsData.shuffled()
            .take(5 )
        val order = Order(
            randomFoodItems,
            Date().time.toString(),
            restaurantNames[restaurantIndex],
            "Delivery Address $i", // Dummy address
            currentLocation
        )
        dummyOrders.add(order)
    }
    return dummyOrders
}
/**
 * Generates a list of restaurants with random food items, ratings, and image URLs.
 */
fun getAllRestaurants(): List<Restaurant> {
    val foodItemsData = listOf(
        FoodItem("Drunken noodles", 15, 4),
        FoodItem("Butter chicken", 25, 4),
        FoodItem("Bento box", 20, 4),
        FoodItem("Seafood ravioli", 30, 4),
        FoodItem("Hand-pulled ramen", 18, 4),
        FoodItem("Chicken taco combo", 22, 4),
        FoodItem("California roll", 12, 4),
        FoodItem("Burger", 15, 4),
        FoodItem("Pizza", 20, 4),
        FoodItem("Fries", 10, 4),
    )

    val restaurantNames = listOf(
        "Three Amigos", "Da Vinci", "Mura", "Siam House",
        "Happy Thai", "Amrit India", "Lan Ramen", "Ami"
    )
    val ratings = listOf(3.5f, 4.5f, 4.5f, 5f, 5f, 4f, 4f, 4f)
    val imageUrlsList = listOf(
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FThree%20Amigos%2F3%20amigos%201.png?alt=media&token=af9a0d02-3634-4618-a8af-8ba6187ca755",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FThree%20Amigos%2F3%20amigos%202.png?alt=media&token=af9a0d02-3634-4618-a8af-8ba6187ca755",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FThree%20Amigos%2F3%20amigos%203.png?alt=media&token=af9a0d02-3634-4618-a8af-8ba6187ca755"
        ),
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FDa%20Vinci%2FDa%20Vinci%201.jpeg?alt=media&token=1c3faa2a-136c-4b4a-814f-bea81f57e7de",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FDa%20Vinci%2FDa%20Vinci%202.jpeg?alt=media&token=1c3faa2a-136c-4b4a-814f-bea81f57e7de",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FDa%20Vinci%2FDa%20Vinci%203.jpeg?alt=media&token=1c3faa2a-136c-4b4a-814f-bea81f57e7de"
        ),
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FMura%2FMura%201.jpeg?alt=media&token=c5261031-22ec-4832-8c44-588e23368885",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FMura%2FMura%202.jpeg?alt=media&token=c5261031-22ec-4832-8c44-588e23368885",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FMura%2FMura%203.jpeg?alt=media&token=c5261031-22ec-4832-8c44-588e23368885"
        ),
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FSiam%20House%2FSiam%20House%201.webp?alt=media&token=7cb20d04-f2de-4bda-8975-f95bf29f368a",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FSiam%20House%2FSiam%20House%202.jpeg?alt=media&token=182a95a3-2b5d-436d-82df-22f196cbb16d",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FSiam%20House%2FSiam%20House%203.jpeg?alt=media&token=182a95a3-2b5d-436d-82df-22f196cbb16d"
        ),
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FHappy%20Thai%2FHappy%20thai%201.jpeg?alt=media&token=b31c4f81-fa6b-4d13-a09b-bd32602ee5e1",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FHappy%20Thai%2FHappy%20thai%202.jpeg?alt=media&token=b31c4f81-fa6b-4d13-a09b-bd32602ee5e1",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FHappy%20Thai%2FHappy%20thai%203.jpeg?alt=media&token=b31c4f81-fa6b-4d13-a09b-bd32602ee5e1"
        ),
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FAmrit%20India%2Famrit%20india%201.jpeg?alt=media&token=81afb834-3ee2-4430-9bb1-448097bb82d4",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FAmrit%20India%2Famrit%20india%202.webp?alt=media&token=e6750475-dd33-4df7-b4a2-d082f0620548",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FAmrit%20India%2Famrit%20india%203.jpeg?alt=media&token=2e9d61e4-1398-401a-a210-b3edca08fb08"
        ),
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FLan%20Ramen%2Flan%20ramen%201%20.jpeg?alt=media&token=b70334d8-648a-4a37-b62d-dc29e4230747",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FLan%20Ramen%2Flan%20ramen%202.jpeg?alt=media&token=b40efc93-38e7-4d5d-a75e-74ad54bdcb13",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FLan%20Ramen%2Flan%20ramen%203.jpeg?alt=media&token=0d7c4fb5-3633-4be2-960f-6f801ec6186a"
        ),
        listOf(
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FAmi%2FAmi%201.jpeg?alt=media&token=def8380c-e04b-4785-a5ef-10e6bac363af",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FAmi%2FAmi%202.jpeg?alt=media&token=def8380c-e04b-4785-a5ef-10e6bac363af",
            "https://firebasestorage.googleapis.com/v0/b/finalapp-172a7.appspot.com/o/restaurants%2FAmi%2FAmi%203.jpeg?alt=media&token=def8380c-e04b-4785-a5ef-10e6bac363af"
        )
    )
    val latLngList = listOf(
        LatLng(39.1719, -86.5353),
        LatLng(39.1647, -86.5325),
        LatLng(39.1720, -86.5115),
        LatLng(39.1654, -86.5284),
        LatLng(39.1653, -86.5264),
        LatLng(39.1674, -86.5334),
        LatLng(39.1650, -86.5326),
        LatLng(39.1641, -86.5151)
    )

    val restaurants = mutableListOf<Restaurant>()

    for (i in restaurantNames.indices) {
        val randomFoodItems = foodItemsData.shuffled()
            .take(5 + Random.nextInt(3)) // Randomly take at least 5 food items
        val restaurant = Restaurant(
            restaurantNames[i],
            ratings[i],
            randomFoodItems,
            imageUrlsList[i], // Empty image list
            latLngList[i]
        )
        restaurants.add(restaurant)
    }

    return restaurants
}
