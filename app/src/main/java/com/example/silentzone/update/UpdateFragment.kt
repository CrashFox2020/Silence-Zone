package com.example.silentzone.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.silentzone.R

import com.example.silentzone.databinding.FragmentUpdateBinding
import com.example.silentzone.location_database.LocationData
import com.example.silentzone.location_database.LocationDataViewModel


class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mLocationDataViewModel: LocationDataViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        binding.updateNameEditText.setText(args.selectedLocationData.name)
        binding.updateLatitudeTextView.setText(args.selectedLocationData.latitude.toString())
        binding.updateLongitudeTextView.setText(args.selectedLocationData.longitude.toString())

        mLocationDataViewModel= ViewModelProvider(this).get(LocationDataViewModel::class.java)

        binding.updateButton.setOnClickListener{
            updateDataToDatabase()
        }

        binding.deleteButton.setOnClickListener{
            deleteDataFromDatabase()
        }
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateDataToDatabase(){
        val longitude=args.selectedLocationData.longitude
        val latitude= args.selectedLocationData.latitude
        val id=args.selectedLocationData.id
        val name=binding.updateNameEditText.text.toString()
        if(!(TextUtils.isEmpty(name))){
            val locationData= LocationData(id,name,latitude,longitude)
            mLocationDataViewModel.upsertLocationData(locationData)
            Toast.makeText(requireContext(),"Successfully updated", Toast.LENGTH_LONG).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(),"Please fill out all fields", Toast.LENGTH_LONG).show()
        }

    }

    private fun deleteDataFromDatabase(){
        val builder=AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_,_->
            mLocationDataViewModel.deleteLocationData(args.selectedLocationData)
            Toast.makeText(requireContext(),"Successfully deleted", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No"){_,_->

        }
        builder.setTitle("Delete ${args.selectedLocationData.name}?")
        builder.setMessage("Are you sure you want to delete ${args.selectedLocationData.name}?")
        builder.create().show()
    }
}