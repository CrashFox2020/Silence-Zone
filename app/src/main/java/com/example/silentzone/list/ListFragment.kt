package com.example.silentzone.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silentzone.location_database.LocationDataViewModel
import com.example.silentzone.R
import com.example.silentzone.databinding.FragmentListBinding


class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var mLocationDataViewModel: LocationDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater, container, false)


        binding.addFAB.setOnClickListener(){
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        val recyclerView=binding.recyclerView
        val locationDataAdapter=LocationDataAdapter()
        recyclerView.adapter=locationDataAdapter
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        mLocationDataViewModel=ViewModelProvider(this).get(LocationDataViewModel::class.java)
        mLocationDataViewModel.getLocationData.observe(viewLifecycleOwner){
            locationDataAdapter.submitList(it)
        }

        val view = binding.root

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}