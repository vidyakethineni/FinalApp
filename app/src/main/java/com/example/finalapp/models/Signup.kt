package com.example.finalapp.models

/**
 * Represents user signup information, including full name, email, password, and an optional profile image URL.
 */
data class Signup(
    var fullName: String? = null,
    var emailId: String? = null,
    var password: String? = null,
    var imageUrl:String?=null,
)