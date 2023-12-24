package com.example.silentzone.change_range

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.silentzone.CommonVariables
import com.example.silentzone.R
import com.example.silentzone.databinding.FragmentChangeRangeBinding

class ChangeRangeFragment : Fragment() {
    private var _binding: FragmentChangeRangeBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangeRangeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.rangeEditText.setText(CommonVariables.range.toString())
        binding.changeRangeButton.setOnClickListener{
            val newRange=binding.rangeEditText.text.toString().toDoubleOrNull()
            if(newRange!=null){
                CommonVariables.range=newRange
                Toast.makeText(requireContext(),"Range changed successfully", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_changeRangeFragment_to_listFragment)
            }
            else{
                Toast.makeText(requireContext(),"Please fill valid values in field", Toast.LENGTH_LONG).show()
            }
        }
        val view = binding.root

        return view
    }


}