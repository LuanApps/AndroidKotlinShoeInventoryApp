package com.udacity.shoestore.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoeDetailBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.viewmodels.ShoeViewModel

class ShoeDetailFragment : Fragment() {
    private val shoeViewModel: ShoeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentShoeDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoe_detail, container, false)

        binding.saveButton.setOnClickListener{
            val shoeName = binding.shoeNameTXT.text.toString()
            val shoeCompany = binding.shoeCompanyTXT.text.toString()
            val shoeSize = binding.shoeSizeTXT.text.toString()
            val shoeDescription = binding.shoeDescriptionTXT.text.toString()

            if (validateInputs(shoeName, shoeCompany, shoeSize, shoeDescription)) {
                val newShoe = Shoe(shoeName, shoeSize.toDouble(), shoeCompany, shoeDescription)
                shoeViewModel.addShoe(newShoe)
                navigateToShoeFragment()
            } else {
                Toast.makeText(context, getString(R.string.invalid_input_warning), Toast.LENGTH_SHORT).show()
            }
        }

        binding.cancelButton.setOnClickListener{
            navigateToShoeFragment()
        }
        return binding.root
    }

    private fun validateInputs(
        shoeName: String,
        shoeCompany: String,
        shoeSizeText: String,
        shoeDescription: String
    ): Boolean {
        return shoeName.isNotBlank() && shoeCompany.isNotBlank() && shoeSizeText.isNotBlank() && shoeDescription.isNotBlank()
    }

    private fun navigateToShoeFragment() {
        findNavController().navigate(ShoeDetailFragmentDirections.actionShoeDetailFragmentToShoesFragment())
    }

}