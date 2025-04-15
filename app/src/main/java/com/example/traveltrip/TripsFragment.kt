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
import androidx.transition.Visibility
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


    @SuppressLint("NotifyDataSetChanged")
    private fun getAllTravels() {
        binding?.progressBar?.visibility = View.VISIBLE
        Model.instance.getAllTravels {
            this.travels = it
            adapter?.updateList(it)
            adapter?.notifyDataSetChanged()


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
            Log.d("btn", "enter ")
            val travel =
                Travel(
                    "Baraka",
                    "the trip is beautiful",
                    "/"
                )
            Model.instance.addTravel(travel) {
                Log.d("btn", "Add to Db ")

            }

            getAllTravels()
            Log.d("btn", "Finish to save with ${this.travels?.size ?: 0}")
        }
    }

}