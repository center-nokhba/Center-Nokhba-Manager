package com.nokhba.center.panel.ui.formation

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.nokhba.center.panel.R
import com.nokhba.center.panel.databinding.FragmentServicesBinding

class FormationFragment : Fragment() {

    private var _binding: FragmentServicesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val servicesViewModel =
            ViewModelProvider(this).get(FormationViewModel::class.java)

        _binding = FragmentServicesBinding.inflate(inflater, container, false)

        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}