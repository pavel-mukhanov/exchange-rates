package com.mukhanov.exchangerates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mukhanov.exchangerates.model.Rate
import com.mukhanov.exchangerates.network.RatesService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ticker

@ObsoleteCoroutinesApi
class RatesViewModel : ViewModel() {
    private var cachedRates: List<Rate> = listOf()
    private val ratesChannel = ticker(delayMillis = 60_000, initialDelayMillis = 0)

    private suspend fun loadRates(): List<Rate> {
        val job = GlobalScope.async {
            RatesService.rates()
        }
        return job.await()
    }

    val rates: LiveData<List<Rate>> = liveData {
        for (event in ratesChannel) {
            cachedRates = loadRates()
            emit(cachedRates)
        }
    }
}