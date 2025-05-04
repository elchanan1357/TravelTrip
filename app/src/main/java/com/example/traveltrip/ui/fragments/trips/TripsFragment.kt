package com.example.traveltrip.ui.fragments.trips


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltrip.BuildConfig
import com.example.traveltrip.ui.adapter.GenericAdapter
import com.example.traveltrip.databinding.RowTripBinding
import com.example.traveltrip.databinding.TripsBinding
import com.example.traveltrip.model.remote.googleApi.Place
import com.example.traveltrip.model.room.entity.Travel
import com.example.traveltrip.ui.viewModel.TripsViewModel
import com.example.traveltrip.utils.getPicFromPicasso
import com.example.traveltrip.utils.log

class TripsFragment : Fragment() {
    private var viewModel: TripsViewModel? = null
    private var binding: TripsBinding? = null
    private var adapter: GenericAdapter<Place, RowTripBinding>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[TripsViewModel::class.java]
    }

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
        val recyclerView: RecyclerView? = binding?.RecyclerViewTrips
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        observeTravels()
//        dropList(mainCategories, subCategories)
    }


    override fun onResume() {
        super.onResume()

        val israelLocations = listOf(
            Pair(32.0853, 34.7818),
//            Pair(31.7683, 35.2137),
//            Pair(32.7940, 34.9896),
//            Pair(29.5581, 34.9482),
//            Pair(32.7957, 35.5311),
//            Pair(33.0084, 35.1016),
//            Pair(40.7128, -74.0060),
//            Pair(34.0522, -118.2437)
        )


        when (arguments?.getString("mainCategory")) {
            "Trips" -> {
                binding?.progressBar?.visibility = View.VISIBLE
                when (arguments?.getString("subCategory")) {
                    "Parks" -> israelLocations.forEach { (lat, lon) ->
                        viewModel?.fetchParks(lat, lon)
                    }

                    "Trips" -> israelLocations.forEach { (lat, lon) ->
//                        viewModel?.fetchTravels(lat, lon)
                    }

                    "Museums" -> israelLocations.forEach { (lat, lon) ->
                        viewModel?.fetchMuseums(lat, lon)
                    }

                    "kids" -> israelLocations.forEach { (lat, lon) ->
//                        viewModel?.fetchTravels(lat, lon)
                    }
                }
            }

            "Hotels" -> {
                binding?.progressBar?.visibility = View.VISIBLE
                var type = ""
                when (arguments?.getString("subCategory")) {
                    "Hostels" -> type = "lodging hostel"
                    "Guest Houses" -> type = "lodging guest house"
                    "Apartments" -> type = "lodging apartment"
                    "Hotels" -> type = "lodging "
                }
                israelLocations.forEach { (lat, lon) ->
                    viewModel?.fetchHotels(lat, lon, type)
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    private fun createAdapter() {
        adapter = GenericAdapter(
            items = emptyList(),
            bindingInflater = RowTripBinding::inflate
        ) { itemBinding, item ->
            itemBinding.Title.text = item.name
            itemBinding.Information.text = item.formattedAddress

            val uri = item.photos?.get(0)?.photoReference?.let { getPhotoUrl(it) }
            log("the uri: ${uri.toString()}")
            getPicFromPicasso(itemBinding.imgBtn, uri)
        }
    }


    fun getPhotoUrl(photoReference: String): String {
        return "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=$photoReference&key=${BuildConfig.GOOGLE_API_KEY}"
    }


    private fun observeTravels() {
        viewModel?.travels?.observe(viewLifecycleOwner) { travel ->
            binding?.progressBar?.visibility = View.GONE
            adapter?.updateList(travel)
        }
    }

//    private fun dropList(mainCategory: String, subCategory: String) {
//        val categoryMap = mapOf(
//            "Flights" to listOf("All Flights", "Low Cost", "Luxury", "Business"),
//            "Trips" to listOf("Museums", "All Trips", "Kids", "Amusement Parks"),
//            "Car Rental" to listOf("Economy", "SUV", "Luxury", "Vans"),
//            "Hotels" to listOf("1 Star", "3 Stars", "5 Stars", "Suites")
//        )
//
//        val mainCategories = categoryMap.keys.toList()
//        var isFirstLoad = true
//
//        binding?.apply {
//            mainCategorySpinner.setAdapterWithItems(mainCategories)
//            mainCategorySpinner.setSelection(mainCategories.indexOfOrDefault(mainCategory))
//
//            updateSubCategorySpinner(categoryMap, mainCategory, subCategory)
//
//            mainCategorySpinner.onItemSelectedListener =
//                object : AdapterView.OnItemSelectedListener {
//                    override fun onItemSelected(
//                        parent: AdapterView<*>,
//                        view: View?,
//                        position: Int,
//                        id: Long
//                    ) {
//                        val selectedMain = mainCategories[position]
//                        val selectedSub =
//                            if (isFirstLoad) subCategory else subCategorySpinner.selectedItem?.toString()
//                                ?: ""
//                        isFirstLoad = false
//                        updateSubCategorySpinner(categoryMap, selectedMain, selectedSub)
//                    }
//
//                    override fun onNothingSelected(parent: AdapterView<*>) {}
//                }
//
//            subCategorySpinner.onItemSelectedListener = simpleListener {
//                // TODO:fetch data from Room and Firestore
//            }
//        }
//    }
//
//
//    private fun Spinner.setAdapterWithItems(items: List<String>) {
//        adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, items).apply {
//            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        }
//    }
//
//    private fun List<String>.indexOfOrDefault(value: String): Int {
//        val index = indexOf(value)
//        return if (index >= 0) index else 0
//    }
//
//    private fun Fragment.updateSubCategorySpinner(
//        categoryMap: Map<String, List<String>>,
//        selectedMain: String,
//        selectedSub: String
//    ) {
//        val subList = categoryMap[selectedMain] ?: emptyList()
//        binding?.subCategorySpinner?.apply {
//            setAdapterWithItems(subList)
//            setSelection(subList.indexOfOrDefault(selectedSub))
//        }
//    }
//
//    private fun simpleListener(onSelect: (String) -> Unit): AdapterView.OnItemSelectedListener {
//        return object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                onSelect(parent.getItemAtPosition(position).toString())
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>) {}
//        }
//    }

}