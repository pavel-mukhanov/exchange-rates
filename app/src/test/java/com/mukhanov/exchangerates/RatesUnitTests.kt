package com.mukhanov.exchangerates

import com.mukhanov.exchangerates.network.RatesService
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RatesUnitTests {
    @Test
    fun ratesUrl() {
        RatesService.rates()
    }
}
