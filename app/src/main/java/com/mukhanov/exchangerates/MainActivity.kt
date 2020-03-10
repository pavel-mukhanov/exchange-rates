package com.mukhanov.exchangerates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.ObsoleteCoroutinesApi

@ObsoleteCoroutinesApi
class MainActivity : AppCompatActivity() {
    private lateinit var ratesView: RecyclerView
    private lateinit var ratesAdapter: RecyclerView.Adapter<*>
    private lateinit var amount: EditText
    private lateinit var progress: View
    private lateinit var emptyView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ratesAdapter = RatesAdapter()
        amount = findViewById(R.id.amount)
        progress = findViewById(R.id.progress)
        emptyView = findViewById(R.id.empty_view)
        ratesView = findViewById<RecyclerView>(R.id.rates_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = ratesAdapter
        }

        amount.addTextChangedListener { text ->
            val amount = text.toString().toFloatOrNull()
            if (amount != null) {
                (ratesAdapter as RatesAdapter).amount = amount
            }
        }

        initAmount()
        loadRates()
    }

    private fun initAmount() {
        val defaultAmount = resources.getInteger(R.integer.default_amount_value).toFloat()
        (ratesAdapter as RatesAdapter).amount = defaultAmount
        amount.setText(defaultAmount.toString())
    }

    private fun loadRates() {
        val model: RatesViewModel by viewModels()
        model.rates.observe(this, Observer { rates ->
            if (rates.isEmpty() && ratesAdapter.itemCount == 0) {
                emptyView.visibility = View.VISIBLE
            }

            if (rates.isNotEmpty()) {
                (ratesAdapter as RatesAdapter).update(rates)
            }
            progress.visibility = View.INVISIBLE
        })
    }
}
