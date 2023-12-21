package com.franklin.fishlabs_capstoneproject.ui.scan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.franklin.fishlabs_capstoneproject.databinding.FragmentScanBinding
import com.franklin.fishlabs_capstoneproject.ui.fish.CameraActivity
import com.franklin.fishlabs_capstoneproject.ui.fish.CameraPickerActivity

class ScanFragment : Fragment() {

    private var _binding: FragmentScanBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val scanViewModel =
            ViewModelProvider(this).get(ScanViewModel::class.java)

        _binding = FragmentScanBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            buttonscan.setOnClickListener {
                Intent(requireActivity(), CameraPickerActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}