package com.example.grocery.managers

import android.content.Context
import android.util.Log
import org.json.JSONObject

class SessionManager(mContext: Context) {
    private val FILE_NAME = "my_prefs"
    private val KEY_TOKEN = "token"
    private val KEY_NAME = "name"
    private val KEY_EMAIL = "email"
    private val KEY_PHONE = "phone"
    private val KEY_LOGGED_IN = "logged_in"
    private val KEY_ID = "userid"

    private var sharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    private var editor = sharedPreferences.edit()

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_LOGGED_IN, false)
    }

    fun logUser(jsonObj: JSONObject) {
        Log.d("Logged", "$jsonObj")
        var token = jsonObj.get("token") as String
        var user = jsonObj.get("user") as JSONObject

        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_NAME, user.get("firstName") as String)
        editor.putString(KEY_EMAIL, user.get("email") as String)
        editor.putString(KEY_PHONE, user.get("mobile") as String)
        editor.putString(KEY_ID, user.get("_id") as String)
        editor.putBoolean(KEY_LOGGED_IN, true)
        editor.commit()
    }

    fun logout(){
        editor.clear().commit()
    }

    fun getName() : String?{
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun getMobile(): String?{
        return sharedPreferences.getString(KEY_PHONE, null)
    }

    fun getEmail(): String?{
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    fun getToken(): String?{
        return sharedPreferences.getString(KEY_TOKEN, null)
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_ID, null)
    }

}