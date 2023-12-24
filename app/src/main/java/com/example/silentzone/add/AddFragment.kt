package com.example.silentzone.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.silentzone.CommonVariables
import com.example.silentzone.location_database.LocationData
import com.example.silentzone.location_database.LocationDataViewModel
import com.example.silentzone.R
import com.example.silentzone.databinding.FragmentAddBinding


class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var mLocationDataViewModel: LocationDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        binding.addLongitudeTextView.setText(CommonVariables.currentLongitude.toString())
        binding.addLatitudeTextView.setText(CommonVariables.currentLatitude.toString())
        val view = binding.root

        mLocationDataViewModel=ViewModelProvider(this).get(LocationDataViewModel::class.java)

        binding.addButton.setOnClickListener(){
            insertDatatoDatabase()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun insertDatatoDatabase(){
        val longitude=binding.addLongitudeTextView.text.toString().toDoubleOrNull()
        val latitude=binding.addLatitudeTextView.text.toString().toDoubleOrNull()
        val id=latitude.toString()+longitude.toString()
        val name=binding.addNameEditText.text.toString()
        if(!(TextUtils.isEmpty(name))&&latitude!=null&&longitude!=null){
            val locationData= LocationData(id,name,latitude,longitude)
            mLocationDataViewModel.upsertLocationData(locationData)
            Toast.makeText(requireContext(),"Successfully added",Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(),"Please fill valid values in all fields",Toast.LENGTH_LONG).show()
        }

    }
}