package com.mukhanov.exchangerates.model

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class RatesDeserializer : JsonDeserializer<Rates> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Rates {
        val entries = json?.asJsonObject?.entrySet()

        val res = entries?.map { (key, value) ->
            val name = value.asJsonObject["Name"].asString
            val v = value.asJsonObject["Value"].asFloat
            Rate(key, name, v)
        }

        return Rates(res.orEmpty())
    }
}