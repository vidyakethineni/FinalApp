package com.example.finalapp.models

/**
 * Represents user information, including name, email, and an optional profile image URL.
 */
data class User(
    var name: String? = null,
    var email: String? = null,
    var profileImage: String? = null,
)
