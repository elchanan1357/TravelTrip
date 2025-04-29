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


        val mainCategoriesList = listOf("Flights","Trips","Car Rental","Hotels")
        val subCategoriesList = listOf("Kids","Museums","All Trips","Amusement parks")

        val mainAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, mainCategoriesList)
        mainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val subAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, subCategoriesList)
        subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        binding?.mainCategorySpinner?.adapter = mainAdapter
        binding?.subCategorySpinner?.adapter = subAdapter


        val mainCategoryIndex = mainCategoriesList.indexOf(mainCategories)
        if (mainCategoryIndex >= 0) {
            binding?.mainCategorySpinner?.setSelection(mainCategoryIndex)
        }


        val subCategoryIndex = subCategoriesList.indexOf(subCategories)
        if (subCategoryIndex >= 0) {
            binding?.subCategorySpinner?.setSelection(subCategoryIndex)
        }


        binding?.mainCategorySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selected = mainCategoriesList[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding?.subCategorySpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selected = subCategoriesList[position]

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        // binding?.mainCategory?.text = mainCategories
        // binding?.subCategory?.text = subCategories
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


}