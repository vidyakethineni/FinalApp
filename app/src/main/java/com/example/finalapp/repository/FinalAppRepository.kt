package com.example.finalapp.repository

import android.net.Uri
import android.util.Log
import com.example.finalapp.FinalApplication
import com.example.finalapp.models.Order
import com.example.finalapp.models.Restaurant
import com.example.finalapp.models.Signup
import com.example.finalapp.models.User
import com.example.finalapp.models.getAllRestaurants
import com.example.finalapp.models.getDummyOrders
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
/**
 * Repository class responsible for handling data operations, authentication, and Firebase interactions.
 */
class FinalAppRepository(application: FinalApplication) {
    private val auth: FirebaseAuth = Firebase.auth

    // Create a storage reference from our app
    private val profileImageRefs = Firebase.storage.reference.child("profile")
    private val db = Firebase.firestore
    /**
     * Performs user signup on Firebase, including uploading the profile image.
     */
    fun performSignupOnFirebase(signup: Signup?, callBack: (Result<*>?) -> Unit) {
        signup?.apply {
            auth.createUserWithEmailAndPassword(
                signup.emailId ?: throw Exception("Invalid email address!"),
                signup.password ?: throw Exception("Invalid password!")
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "createUserWithEmail:success")
                        uploadProfileImage(signup, callBack)
                    } else {
                        callBack(Result.failure<Exception>(Exception("Failed to register!")))
                    }
                }
        }
    }

    private fun uploadProfileImage(
        signup: Signup,
        callBack: (Result<*>?) -> Unit
    ) {
        val imageRefs =
            profileImageRefs.child((Uri.parse(signup.imageUrl).lastPathSegment.toString()))
        val uploadTask = imageRefs.putFile(Uri.parse(signup.imageUrl))
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                callBack(Result.failure<Exception>(Exception(task.exception?.localizedMessage)))
            }
            imageRefs.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                signup.apply {
                    val user = User(fullName, emailId, downloadUri.toString())
                    db.collection("users").document(user.email!!)
                        .set(user).addOnSuccessListener {
                            callBack(Result.success("User registered Successfully!"))
                        }.addOnFailureListener {
                            callBack(Result.failure<Exception>(Exception("Failed to register!")))
                        }
                }
            } else {
                // Handle failures
                callBack(Result.failure<Exception>(Exception("Failed to register!")))
            }
        }
    }

    fun performLogin(signup: Signup?, callBack: (Result<*>?) -> Unit) {
        signup?.apply {
            auth.signInWithEmailAndPassword(
                signup.emailId ?: throw Exception("Invalid email address!"),
                signup.password ?: throw Exception("Invalid password!")
            )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "createUserWithEmail:success")
                        callBack(Result.success("Logged In Successfully!"))
                    } else {
                        callBack(Result.failure<Exception>(Exception("Invalid email or password!")))
                    }
                }
        }
    }
    /**
     * Fetches details of the currently logged-in user.
     */
    fun fetchUserDetails(callBack: (Result<*>?) -> Unit) {
        db.collection("users").document(auth.currentUser?.email ?: return).get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                callBack(Result.success(user))
            }.addOnFailureListener {
                callBack(Result.failure<Exception>(it))
            }
    }
    /**
     * Uploads sample data if not available in the Firestore database.
     */
    fun uploadDummyDataIfNotAvailable() {
        val restaurantsMap: Map<String, Restaurant> =
            getAllRestaurants().associateBy { it.name ?: return }
        val restaurantsCollection = db.collection("users")
            .document(auth.currentUser?.email ?: return).collection("restaurants")
        restaurantsCollection.get().addOnCompleteListener {
            if (it.result.isEmpty) {
                val batch = db.batch()
                restaurantsMap.forEach { restaurant ->
                    val newDocRef =
                        restaurantsCollection.document(restaurant.key) // Create a new document reference
                    batch.set(newDocRef, restaurant.value) // Add set operation to the batch
                }
                val ordersCollection = db.collection("users")
                    .document(auth.currentUser?.email ?: return@addOnCompleteListener)
                    .collection("orders")
                getDummyOrders().forEach { order ->
                    val newDocRef = ordersCollection.document() // Create a new document reference
                    batch.set(newDocRef, order) // Add set operation to the batch
                }

                batch.commit().addOnSuccessListener {

                }.addOnFailureListener {

                }
            } else {
                return@addOnCompleteListener
            }
        }
    }
    /**
     * Fetches all restaurants and recent orders for the currently logged-in user.
     */
    fun fetchAllRestaurants(
        callBack: (Result<*>?) -> Unit,
        recentFiveRestaurants: (Result<*>?) -> Unit
    ) {
        val restaurantsCollection = db.collection("users")
            .document(auth.currentUser?.email ?: return).collection("restaurants")
        restaurantsCollection.get().addOnCompleteListener { it ->
            if (it.isSuccessful) {
                val list = mutableListOf<Restaurant>()
                for (snapShot in it.result) {
                    list.add(snapShot.toObject(Restaurant::class.java))
                }
                val ordersCollection = db.collection("users")
                    .document(auth.currentUser?.email ?: return@addOnCompleteListener)
                    .collection("orders")
                ordersCollection.orderBy("dateTime").get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        val orderList = mutableListOf<Order>()
                        for (snapShot in it.result) {
                            val order = snapShot.toObject(Order::class.java)
                            orderList.add(order)
                        }
                        callBack(Result.success(list))
                        recentFiveRestaurants(Result.success(orderList))
                    } else {
                        recentFiveRestaurants(Result.failure<Exception>(Exception("Failed to fetch orders!")))
                    }
                }
            } else {
                callBack(Result.failure<Exception>(Exception("Failed to fetch restaurants")))
            }
        }
    }


    fun fetchRestaurant(name: String, callBack: (Result<*>?) -> Unit) {
        val restaurantsCollection = db.collection("users")
            .document(auth.currentUser?.email ?: return).collection("restaurants")
        restaurantsCollection.document(name).get()
            .addOnSuccessListener {
                val restaurant = it.toObject(Restaurant::class.java)
                callBack(Result.success(restaurant))
            }.addOnFailureListener {
                callBack(Result.failure<Exception>(it))
            }
    }

    fun placeOrder(order: Order, callBack: (Result<*>?) -> Unit) {
        val ordersCollection = db.collection("users")
            .document(auth.currentUser?.email ?: return)
            .collection("orders")
        ordersCollection.document().set(order).addOnSuccessListener {
            callBack(Result.success(order))
        }.addOnFailureListener {
            callBack(Result.failure<Exception>(it))
        }
    }

    fun fetchAllOrders(callBack: (Result<*>?) -> Unit) {
        val ordersCollection = db.collection("users")
            .document(auth.currentUser?.email ?: return)
            .collection("orders")
        ordersCollection.orderBy("dateTime").get().addOnCompleteListener {
            if (it.isSuccessful) {
                val orderList = mutableListOf<Order>()
                for (snapShot in it.result) {
                    val order = snapShot.toObject(Order::class.java)
                    orderList.add(order)
                }
                callBack(Result.success(orderList))
            } else {
                callBack(Result.failure<Exception>(Exception("Failed to fetch orders!")))
            }
        }
    }

}