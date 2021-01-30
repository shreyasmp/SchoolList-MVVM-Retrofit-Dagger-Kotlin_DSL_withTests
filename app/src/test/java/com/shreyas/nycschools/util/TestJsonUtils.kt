package com.shreyas.nycschools.util

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets

/**
 *  Utility class to read assets folder in test and bring the recorded json object as json response string
 */
object TestJsonUtils {

    private val TAG = TestJsonUtils::class.java.simpleName

    private fun getJsonDataFromAsset(fileName: String): String? {
        val jsonString: String
        try {
            val inputStream: InputStream =
                this.javaClass.classLoader!!.getResourceAsStream(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            jsonString = String(buffer, StandardCharsets.UTF_8)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun <T> getJson(fileName: String, tClass: Class<T>?): List<T>? {
        val jsonString = getJsonDataFromAsset(fileName)
        Log.i(TAG, "JSON String: $jsonString")
        val listType = object : TypeToken<List<T>>() {}.type
        return Gson().fromJson(jsonString, listType)
    }

    fun <T> getObjectFromJsonFile(
        jsonFile: String,
        tClass: Class<T>?
    ): T? {
        var inputStream: InputStream? = null
        try {
            inputStream = this.javaClass.classLoader!!.getResourceAsStream(jsonFile)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            val json = String(buffer, StandardCharsets.UTF_8)
            return getObjectFromJsonString(json, tClass)
        } catch (exception: Exception) {
            Log.d(TAG, "Exception: ${exception.message}")
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close()
                } catch (exception: IOException) {
                    Log.d(TAG, "IOException: ${exception.message}")
                }
            }
        }
        return null
    }

    private fun <T> getObjectFromJsonString(
        jsonData: String?,
        tClass: Class<T>?
    ): T {
        return Gson().fromJson(jsonData, tClass)
    }

    fun getJsonAsString(fileName: String): String {
        val location = this.javaClass.classLoader!!.getResource(fileName)
        val file = File(location.path)
        return String(file.readBytes())
    }
}