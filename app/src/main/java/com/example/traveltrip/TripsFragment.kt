package com.example.traveltrip


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltrip.adapter.GenericAdapter
import com.example.traveltrip.databinding.RowTripBinding
import com.example.traveltrip.databinding.TripsBinding
import com.example.traveltrip.model.Model
import com.example.traveltrip.model.Travel

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

        getAllTravels()
        createAdapter()

        val recyclerView: RecyclerView? = binding?.tripsRecyclerViewTrips
        recyclerView?.setHasFixedSize(true)
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
    }


    override fun onResume() {
        super.onResume()
        getAllTravels()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getAllTravels() {
        Model.instance.getAllTravels {
            this.travels = it
            adapter?.notifyDataSetChanged()
        }
    }


    private fun createAdapter() {
        adapter = GenericAdapter(
            items = travels,
            bindingInflater = RowTripBinding::inflate
        ) { itemBinding, item ->
            itemBinding.rowTripTitle.text = item.title
            itemBinding.rowTripInformation.text = item.info
        }
    }
}