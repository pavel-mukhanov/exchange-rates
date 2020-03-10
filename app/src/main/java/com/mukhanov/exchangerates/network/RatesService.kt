package com.mukhanov.exchangerates.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mukhanov.exchangerates.model.Rate
import com.mukhanov.exchangerates.model.Rates
import com.mukhanov.exchangerates.model.RatesDeserializer
import com.mukhanov.exchangerates.model.Response
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlin.Result.Companion.failure
import kotlin.Result.Companion.success

object RatesService {
    private const val URL = "https://www.cbr-xml-daily.ru/daily_json.js"

    private val client = OkHttpClient()
    private val ratesRequest: Request = Request.Builder()
        .url(URL)
        .build()
    private val gson: Gson

    init {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(Rates::class.java, RatesDeserializer())
        gson = builder.create()
    }

    private fun ratesRequest(): Result<String> {
        return try {
            client.newCall(ratesRequest).execute().use { response ->
                success(response.body!!.string())
            }
        } catch (e: IOException) {
            failure(e)
        }
    }

    fun rates(): List<Rate> {
        val resp = ratesRequest().getOrElse {
            return emptyList()
        }

        return gson.fromJson(resp, Response::class.java).valute.rates
    }
}
