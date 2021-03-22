package com.moin.sparknetworks.utils

import android.content.Context
import java.io.InputStream

 fun readJSONFromAsset(applicationContext: Context?): String? {
    val json: String?
    try {
        val inputStream: InputStream? =  applicationContext?.assets?.open("personality_test.json")
        json = inputStream?.bufferedReader().use { it?.readText() }

    } catch (ex: Exception) {
        ex.printStackTrace()
        return null
    }
    return json
}

fun rand(start: Int, end: Int): Int {
    require(start <= end) { "Illegal Argument" }
    return (start..end).random()
}