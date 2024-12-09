package com.example.chiemtinh
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.google.gson.Gson

class AstrologyService(private val apiKey: String) {
    private val client = OkHttpClient()

    suspend fun getAstrologyData(location: String, date: String): AstrologyResponse? {
        val url = "https://api.weatherapi.com/v1/astronomy.json?key=$apiKey&q=$location&dt=$date"

        val request = Request.Builder()
            .url(url)
            .build()

        return withContext(Dispatchers.IO) {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                responseBody?.let {
                    Gson().fromJson(it, AstrologyResponse::class.java)
                }
            } else {
                // Log lỗi hoặc xử lý theo cách khác
                null
            }
        }
    }
}