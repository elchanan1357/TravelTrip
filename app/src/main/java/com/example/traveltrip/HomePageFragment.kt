package com.example.traveltrip

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.traveltrip.databinding.HomePageBinding


class HomePageFragment : Fragment() {
    private var binding: HomePageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomePageBinding.inflate(inflater, container, false)

        binding?.BlogIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_blogs) }
        binding?.DiscoverIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_discover) }
//         binding?.homeTripsIcon?.setOnClickListener { findNavController().navigate(R.id.action_home_trips) }

//        val textView: TextView? = binding?.textView15
//        val iconLocation = ContextCompat.getDrawable(requireContext(), R.drawable.icon_location)
//
//        iconLocation?.setBounds(0, 0, 20.dpToPx(), 20.dpToPx())
//
//        val spannableString = SpannableString(getString(R.string.cusco_peru))
//        val imageSpan = ImageSpan(iconLocation!!, ImageSpan.ALIGN_BASELINE)
//
//        spannableString.setSpan(imageSpan, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//        textView?.text = spannableString

        return binding?.root
    }

//    fun Int.dpToPx(): Int {
//        val density = Resources.getSystem().displayMetrics.density
//        return (this * density).toInt()
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
