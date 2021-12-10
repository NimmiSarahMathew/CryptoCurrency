package com.example.cryptocurrency

import android.content.Context
import android.os.Build
import android.util.Log
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {

    /**
     * method to format the date
     */
    fun getCurrentDate():String{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            var answer: String =  current.format(formatter)
            return answer
        } else {
            var date = Date()
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val answer: String = formatter.format(date)
            return answer
        }
    }
}