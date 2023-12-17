package com.example.finalapp.ui.utils
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import androidx.core.content.FileProvider
import com.example.finalapp.models.FoodItem
import com.google.firebase.firestore.auth.User
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.math.RoundingMode
import java.text.DecimalFormat


object Utils {
    fun isValidEmail(email: String?): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(pwd: String): Boolean {
        return !TextUtils.isEmpty(pwd) && pwd.length > 5
    }

    fun isOnline(context: Context): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            //should check null because in airplane mode it will be null
            netInfo != null && netInfo.isConnected
        } catch (e: NullPointerException) {
            false
        }
    }

    val restaurantsList: List<String> = mutableListOf(
            "Asset Management",
            "Secure Configuration of Assets",
            "Patch and Vulnerability Management",
            "Control Access and Monitor Use of Administrative Privileges",
            "Malware Protection",
            "Application Software Security",
            "Basic Log Monitoring",
            "Network Defence",
            "Data Recovery Capability",
            "Basic Security Awareness Training",
            "Data Protection",
            "Incident Response and Management",
            "Wireless Access Control",
            "Physical Security",
            "Penetration Testing"
        )

    val foodItemsData = listOf(
        FoodItem("Drunken noodles", 15, 4),
        FoodItem("Butter chicken", 25, 4),
        FoodItem("Bento box", 20, 4),
        FoodItem("Seafood ravioli", 30, 4),
        FoodItem("Hand-pulled ramen", 18, 4),
        FoodItem("Chicken taco combo", 22, 4),
        FoodItem("California roll", 12, 4)
    )



     fun bitmapToFile(bitmap: Bitmap,applicationContext: Application,imageFile:File): Uri? {
        try {
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return FileProvider.getUriForFile(applicationContext, "${applicationContext.packageName}.provider", imageFile)
    }


}