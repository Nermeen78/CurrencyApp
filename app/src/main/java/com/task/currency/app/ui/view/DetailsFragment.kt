package com.task.currency.app.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.task.currency.app.data.model.CurrencyRate
import com.task.currency.app.databinding.FragmentDetailsBinding
import com.task.currency.app.ui.adapters.OtherCurrenciesAdapter
import com.task.currency.app.ui.viewmodel.DetailsViewModel
import com.task.currency.app.util.Status
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

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
    private val detailsViewModel: DetailsViewModel by viewModels()
    private var adapter: OtherCurrenciesAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        loadOtherCurrencies()
        _binding?.lifecycleOwner = this
        return binding.root
    }

    private fun loadOtherCurrencies() {
        adapter = OtherCurrenciesAdapter(currencyRateList = this.otherCurrenciesList)
        _binding?.otherCurrenciesAdapter = adapter
        _binding?.recyclerViewOtherCurrencies?.adapter = adapter
        detailsViewModel.getLatestPopularCurrencies().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    _binding?.progressBar?.visibility = View.GONE
                    it.data?.let { result ->
                        Log.d("RRRRRRR","TEEEEEEEEEEEe${otherCurrenciesList.size} ${result.getCurrenciesRates().size}")
                        otherCurrenciesList.clear()
                        otherCurrenciesList.addAll(result.getCurrenciesRates())

                        updateCurrencyList()
                        Log.d("RRRRRR","TEEEEEEEEEEEe${otherCurrenciesList.size}")

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

    private fun updateCurrencyList() {
        adapter?.notifyDataSetChanged()
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