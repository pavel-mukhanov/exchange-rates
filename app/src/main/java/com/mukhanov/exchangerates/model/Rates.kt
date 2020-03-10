package com.mukhanov.exchangerates.model

import com.google.gson.annotations.SerializedName

data class Response(@SerializedName("Valute") val valute: Rates = Rates())

data class Rates(val rates: List<Rate> = listOf())
data class Rate(val id: String, val name: String, val value: Float, val amount: Int = 1)
