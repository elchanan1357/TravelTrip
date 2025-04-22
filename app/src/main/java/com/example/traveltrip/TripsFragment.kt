package com.example.traveltrip


import android.annotation.SuppressLint
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
import com.example.traveltrip.model.ModelTravel
import com.example.traveltrip.model.entity.Travel

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


    //TODO Delete it
    private fun tryMakeDelete() {
        binding?.addBtn?.setOnClickListener {
            Log.d("logs", "enter ")
            val travel =
                Travel(
                    "Baraka",
                    "the trip is beautiful",
                    "/"
                )
            ModelTravel.instance.addTravel(travel) {
                Log.d("logs", "Add to Db ")
            }

            getAllTravels()
            Log.d("logs", "Finish to save with ${this.travels?.size ?: 0}")
        }
    }

}