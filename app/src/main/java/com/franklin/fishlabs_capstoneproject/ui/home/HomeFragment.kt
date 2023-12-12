package com.franklin.fishlabs_capstoneproject.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.franklin.fishlabs_capstoneproject.databinding.FragmentHomeBinding
import com.franklin.fishlabs_capstoneproject.ui.freshwaterfish.FreshwaterFishActivity
import com.franklin.fishlabs_capstoneproject.ui.seawaterfish.FishListActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            bannerCategory1.setOnClickListener {
                Intent(requireActivity(), FishListActivity::class.java).apply {
                    startActivity(this)
                }
            }

            bannerCategory2.setOnClickListener{
                Intent(requireActivity(), FreshwaterFishActivity::class.java).apply {
                    startActivity(this)
                }
            }
            fun onDestroyView() {
                super.onDestroyView()
                _binding = null
            }
        }
    }
}