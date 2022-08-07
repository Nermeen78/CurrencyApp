package com.task.currency.app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.task.currency.app.BR
import com.task.currency.app.data.model.CurrencyAdapterItem
import com.task.currency.app.data.model.CurrencyInfo
import com.task.currency.app.databinding.ItemHeaderBinding
import com.task.currency.app.databinding.ItemHistoricalCurrencyBinding


class HistoricalCurrenciesAdapter(private val currencyList: List<CurrencyAdapterItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_TYPE = 0
    val HEADER_TYPE = 1

    class ItemViewHolder(private val binding: ItemHistoricalCurrencyBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currencyInfo: CurrencyInfo) {
            binding.setVariable(BR.currencyItemModel, currencyInfo)
        }
    }
    class HeaderViewHolder(private val binding: ItemHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(header: String) {
            binding.setVariable(BR.header, header)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currencyList[position].isHeader)
            HEADER_TYPE
        else
            ITEM_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==ITEM_TYPE) {
            val binding = ItemHistoricalCurrencyBinding.inflate(LayoutInflater.from(parent.context))
            return ItemViewHolder(binding)
        }
        else
        {
            val binding = ItemHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return HeaderViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(currencyList[position].isHeader) {
            val header = currencyList[position].header
            ( holder as HeaderViewHolder).bind(header)
        }
        else
        {
            val currencyInfo = currencyList[position].currencyInfo
            if (currencyInfo != null) {
                ( holder as ItemViewHolder).bind(currencyInfo)
            }

        }
    }

    override fun getItemCount(): Int =
        currencyList.size


}

