package com.example.grocery.apps

import java.text.ParseException
import java.text.SimpleDateFormat

class Config {
    companion object {
        const val BASE_URL = "http://grocery-second-app.herokuapp.com/api/"
        const val IMAGE_URL = "http://rjtmobile.com/grocery/images/"

        val IS_FREE_DELIVERY = 300
        val DELIVERY_COST = 30

        fun convertMongoDate(date: String): String? {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val outputFormat = SimpleDateFormat("MMM d, yyyy")
            try {
                val finalStr: String = outputFormat.format(inputFormat.parse(date))
                println(finalStr)
                return finalStr
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return ""
        }
    }
}