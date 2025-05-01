package com.example.traveltrip


import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltrip.adapter.GenericAdapter
import com.example.traveltrip.databinding.RowTripBinding
import com.example.traveltrip.databinding.TripsBinding
import com.example.traveltrip.model.ModelTravel
import com.example.traveltrip.model.entity.Travel
import com.example.traveltrip.utils.log

class TripsFragment : Fragment() {
    private var travels: List<Travel>? = null
    private var binding: TripsBinding? = null
    private var adapter: GenericAdapter<Travel, RowTripBinding>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TripsBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Toast.makeText(requireContext(), "New test", Toast.LENGTH_SHORT).show()
        val mainCategories = arguments?.getString("mainCategory") ?: ""
        val subCategories = arguments?.getString("subCategory") ?: ""

        createAdapter()
        getAllTravels()
        log(mainCategories)
        log(subCategories)

        val recyclerView: RecyclerView? = binding?.RecyclerViewTrips
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        dropList(mainCategories, subCategories)

    }


    override fun onResume() {
        super.onResume()
        getAllTravels()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    private fun getAllTravels() {
        binding?.progressBar?.visibility = View.VISIBLE
        ModelTravel.instance.getAllTravels {
            this.travels = it
            adapter?.updateList(it)

            binding?.progressBar?.visibility = View.GONE
        }
    }


    private fun createAdapter() {
        adapter = GenericAdapter(
            items = travels,
            bindingInflater = RowTripBinding::inflate
        ) { itemBinding, item ->
            itemBinding.Title.text = item.title
            itemBinding.Information.text = item.info
        }
    }


    private fun dropList(mainCategory: String, subCategory: String) {
        val categoryMap = mapOf(
            "Flights" to listOf("All Flights", "Low Cost", "Luxury", "Business"),
            "Trips" to listOf("Museums", "All Trips", "Kids", "Amusement Parks"),
            "Car Rental" to listOf("Economy", "SUV", "Luxury", "Vans"),
            "Hotels" to listOf("1 Star", "3 Stars", "5 Stars", "Suites")
        )

        val mainCategories = categoryMap.keys.toList()
        var isFirstLoad = true

        binding?.apply {
            mainCategorySpinner.setAdapterWithItems(mainCategories)
            mainCategorySpinner.setSelection(mainCategories.indexOfOrDefault(mainCategory))

            updateSubCategorySpinner(categoryMap, mainCategory, subCategory)

            mainCategorySpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedMain = mainCategories[position]
                        val selectedSub =
                            if (isFirstLoad) subCategory else subCategorySpinner.selectedItem?.toString()
                                ?: ""
                        isFirstLoad = false
                        updateSubCategorySpinner(categoryMap, selectedMain, selectedSub)
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {}
                }

            subCategorySpinner.onItemSelectedListener = simpleListener {
                // TODO:fetch data from Room and Firestre
            }
        }
    }

    private fun Spinner.setAdapterWithItems(items: List<String>) {
        adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, items).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun List<String>.indexOfOrDefault(value: String): Int {
        val index = indexOf(value)
        return if (index >= 0) index else 0
    }

    private fun Fragment.updateSubCategorySpinner(
        categoryMap: Map<String, List<String>>,
        selectedMain: String,
        selectedSub: String
    ) {
        val subList = categoryMap[selectedMain] ?: emptyList()
        binding?.subCategorySpinner?.apply {
            setAdapterWithItems(subList)
            setSelection(subList.indexOfOrDefault(selectedSub))
        }
    }

    private fun simpleListener(onSelect: (String) -> Unit): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                onSelect(parent.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

}