package com.task.currency.app.ui.view

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.task.currency.app.R
import com.task.currency.app.databinding.FragmentMainBinding
import com.task.currency.app.ui.viewmodel.MainViewModel
import com.task.currency.app.util.Status
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var currencySymbols = arrayListOf<String>()
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        _binding?.buttonDetails?.setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_detailsFragment)
        }
        _binding?.mainViewModel = mainViewModel
        _binding?.lifecycleOwner = this
        loadCurrencySymbols()
        setupUiActions()
        return view
    }

    private fun setupUiActions() {

        _binding?.txtAmount?.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                if (event == null || !event.isShiftPressed) {
                    // the user is done typing.
                    callConvert(view.text.toString())
                    true// consume.
                } else
                    false
            } else
                false
        }
        _binding?.txtConvertedValue?.setOnEditorActionListener { _, actionId, event ->

            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event != null &&
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                if (event == null || !event.isShiftPressed) {
                    // the user is done typing.
                    callConvert(_binding?.txtConvertedValue?.text.toString(), true)
                    true// consume.
                } else
                    false
            } else
                false


        }
        _binding?.spinnerTo?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                callConvert(_binding?.txtAmount?.text.toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        _binding?.spinnerFrom?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    callConvert(_binding?.txtAmount?.text.toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
        _binding?.buttonSwitch?.setOnClickListener {
            val tempPos = _binding?.spinnerTo?.selectedItemPosition
            _binding?.spinnerTo?.setSelection(
                _binding?.spinnerFrom?.selectedItemPosition ?: 0,
                true
            )
            _binding?.spinnerFrom?.setSelection(tempPos ?: 0, true)
        }
    }

    private fun callConvert(amount: String, isReverse: Boolean = false) {
        //To Ignore First Load
        if (amount != "0") {
            (if (isReverse)
                mainViewModel.convert(
                    _binding?.spinnerFrom?.selectedItem.toString(),
                    _binding?.spinnerTo?.selectedItem.toString(),
                    amount, isReverse
                )
            else
                mainViewModel.convert(
                    _binding?.spinnerTo?.selectedItem.toString(),
                    _binding?.spinnerFrom?.selectedItem.toString(),
                    amount, isReverse
                )).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.SUCCESS -> {
                        _binding?.progressBar?.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        _binding?.progressBar?.visibility = View.VISIBLE

                    }
                    Status.ERROR -> {
                        //Handle Error
                        _binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun loadCurrencySymbols() {
        val adapter = ArrayAdapter(
            this.requireContext(),
            android.R.layout.simple_spinner_item,
            currencySymbols
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        _binding?.spinnerTo?.adapter = adapter
        _binding?.spinnerFrom?.adapter = adapter

        mainViewModel.getCurrencySymbols().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    _binding?.progressBar?.visibility = View.GONE
                    it.data?.let { result ->
                        Log.d("Error", "error: ${adapter.count}  $currencySymbols")
                        this.currencySymbols.addAll(result.symbols.keys)
                    }
                    adapter.notifyDataSetChanged()
                    Log.d("Error", "error: ${adapter.count}  $currencySymbols")
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @return A new instance of fragment MainFragment.
         */
        @JvmStatic
        fun newInstance() =
            MainFragment()
    }
}