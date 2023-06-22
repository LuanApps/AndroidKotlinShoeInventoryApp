package com.udacity.shoestore.fragments

import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentShoesBinding
import com.udacity.shoestore.databinding.ShoeItemBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.viewmodels.ShoeViewModel

class ShoesFragment : Fragment() {

    private val shoeViewModel: ShoeViewModel by activityViewModels()
    private lateinit var binding: FragmentShoesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shoes, container, false)

        binding.viewModel = shoeViewModel
        binding.lifecycleOwner = this

        binding.floatingActionButton.setOnClickListener{
            findNavController().navigate(ShoesFragmentDirections.actionShoesFragmentToShoeDetailFragment())
        }

        shoeViewModel.shoeList.observe(viewLifecycleOwner, Observer { shoes ->
            for (shoe in shoes) {
                updateShoeListView(shoe)
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(ShoesFragmentDirections.actionShoesFragmentToLoginFragment())
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)

    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onPrepareMenu(menu: Menu) {
                // Handle for example visibility of menu items
            }

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId){
                    android.R.id.home -> {
                        findNavController().navigate(ShoesFragmentDirections.actionShoesFragmentToLoginFragment())
                        return true
                    }
                    R.id.menu_logout -> {
                        findNavController().navigate(ShoesFragmentDirections.actionShoesFragmentToLoginFragment())
                        return true
                    }
                    else -> return true
                }

            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun updateShoeListView(shoe: Shoe) {

        val itemBinding: ShoeItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.shoe_item,
            null,
            false
        )
        itemBinding.shoe = shoe
        binding.shoeListLinearLayout.addView(itemBinding.root)
    }

}