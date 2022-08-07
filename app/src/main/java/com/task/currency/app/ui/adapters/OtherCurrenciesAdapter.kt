package com.task.currency.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.currency.app.BR
import com.task.currency.app.data.model.CurrencyRate
import com.task.currency.app.databinding.ItemCurrencyBinding


class OtherCurrenciesAdapter(private val currencyRateList: List<CurrencyRate>): RecyclerView.Adapter<OtherCurrenciesAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ItemCurrencyBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currencyRate: CurrencyRate) {
            binding.setVariable(BR.currencyItemModel, currencyRate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context))
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currencyRate: CurrencyRate = currencyRateList[position]
        holder.bind(currencyRate)

    }

    override fun getItemCount(): Int =
       currencyRateList.size

}