package com.example.shuffletalksapp.session

import android.content.Context
import android.content.SharedPreferences
import com.example.shuffletalksapp.util.GlobalApplication


class SessionManager() {

    private var pref: SharedPreferences
    private var editor: SharedPreferences.Editor? = null
    private val PRIVATE_MODE = 0
    private var _context: Context? = null

    // Sharedpref file name
    private val PREF_NAME = "ShuffleTalk"
    private val IS_LOGIN = "IsLoggedIn"
    val KEY_USERNAME = "username"
    val KEY_USERID = "user_id"

    init {
        _context = GlobalApplication.getContext()
        pref = _context!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun createSession(username: String, userId: String) {

        editor?.putBoolean(IS_LOGIN, true)
        editor?.putString(KEY_USERNAME, username)
        editor?.putString(KEY_USERID, userId)
        editor?.apply()

    }

    fun getUserDetails(): HashMap<String, String?> {

        val user = HashMap<String, String?>()

        user[KEY_USERNAME] = pref.getString(KEY_USERNAME, "no_user")
        user[KEY_USERID] = pref.getString(KEY_USERID, null)

        return user

    }

    fun logoutUser() {
        editor!!.clear()
        editor!!.commit()
    }

    fun checkLogin() {
        if (!this.isLoggedIn()) {
            //activity.getNavcontroller().navigate(R.id.loginFragment)
        }
    }

    fun isLoggedIn(): Boolean {
        return pref.getBoolean(IS_LOGIN, false)
    }
}