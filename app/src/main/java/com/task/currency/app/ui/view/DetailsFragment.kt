package com.task.currency.app.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.task.currency.app.R
import com.task.currency.app.data.model.CurrencyAdapterItem
import com.task.currency.app.data.model.CurrencyInfo
import com.task.currency.app.data.model.CurrencyRate
import com.task.currency.app.databinding.FragmentDetailsBinding
import com.task.currency.app.ui.adapters.HistoricalCurrenciesAdapter
import com.task.currency.app.ui.adapters.OtherCurrenciesAdapter
import com.task.currency.app.ui.viewmodel.DetailsViewModel
import com.task.currency.app.util.Status
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var otherCurrenciesList = arrayListOf<CurrencyRate>()
    private var historicalCurrenciesList = arrayListOf<CurrencyAdapterItem>()
    private val detailsViewModel: DetailsViewModel by viewModels()
    private var otherCurrenciesAdapter: OtherCurrenciesAdapter? = null
    private var historicalCurrenciesAdapter: HistoricalCurrenciesAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        loadOtherCurrencies()
        loadHistoricalCurrencies()
        _binding?.lifecycleOwner = this
        _binding?.btnBack?.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_detailsFragment_to_mainFragment)
        }
        return binding.root
    }

    private fun loadOtherCurrencies() {
        otherCurrenciesAdapter = OtherCurrenciesAdapter(currencyRateList = this.otherCurrenciesList)
        _binding?.otherCurrenciesAdapter = otherCurrenciesAdapter
        _binding?.recyclerViewOtherCurrencies?.adapter = otherCurrenciesAdapter
        detailsViewModel.getLatestPopularCurrencies().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    _binding?.progressBar?.visibility = View.GONE
                    it.data?.let { result ->
                        otherCurrenciesList.clear()
                        otherCurrenciesList.addAll(result.getCurrenciesRates())
                        updateCurrencyList()

                    }

                }
                Status.LOADING -> {
                    _binding?.progressBar?.visibility = View.VISIBLE

                }
                Status.ERROR -> {
                    //Handle Error
                    _binding?.progressBar?.visibility = View.GONE
                    Log.d("Error", "error: ${it.message}")
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun loadHistoricalCurrencies() {
        historicalCurrenciesAdapter = HistoricalCurrenciesAdapter(historicalCurrenciesList)
        _binding?.historicalCurrenciesAdapter = historicalCurrenciesAdapter
        detailsViewModel.historicalCurrencyInfo.observe(viewLifecycleOwner) {
            historicalCurrenciesList.clear()
            var header=""
            it.map { item->
                if(header!=item.date)
                {
                    header=item.date
                    historicalCurrenciesList.add(CurrencyAdapterItem(null,header,true))

                }
                historicalCurrenciesList.add(CurrencyAdapterItem(item,"",false))
            }
            historicalCurrenciesAdapter?.notifyDataSetChanged()
        }
    }

    private fun updateCurrencyList() {
        otherCurrenciesAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment DetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            DetailsFragment()
    }
}