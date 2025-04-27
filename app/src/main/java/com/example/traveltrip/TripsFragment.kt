package com.example.traveltrip


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltrip.adapter.GenericAdapter
import com.example.traveltrip.databinding.RowTripBinding
import com.example.traveltrip.databinding.TripsBinding
import com.example.traveltrip.model.ModelTrip
import com.example.traveltrip.model.entity.Trip


class TripsFragment : Fragment() {
    private var travels: List<Trip>? = null
    private var binding: TripsBinding? = null
    private var adapter: GenericAdapter<Trip, RowTripBinding>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TripsBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createAdapter()
        getAllTravels()

        val recyclerView: RecyclerView? = binding?.RecyclerViewTrips
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        tryMakeDelete()
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
        ModelTrip.instance.getAllTravels {
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


    //TODO Delete it
    private fun tryMakeDelete() {
        binding?.addBtn?.setOnClickListener {
            Log.d("logs", "enter ")
            val travel =
                Trip(
                    "Baraka",
                    "the trip is beautiful",
                    "/"
                )
            ModelTrip.instance.addTravel(travel) {
                Log.d("logs", "Add to Db ")
            }

            getAllTravels()
            Log.d("logs", "Finish to save with ${this.travels?.size ?: 0}")
        }
    }

}