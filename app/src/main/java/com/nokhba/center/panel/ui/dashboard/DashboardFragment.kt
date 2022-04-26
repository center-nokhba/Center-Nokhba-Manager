package com.nokhba.center.panel.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nokhba.center.panel.databinding.FragmentDashboardBinding
import com.nokhba.center.panel.model.Participant

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var database: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val root: View = binding.root

        database = Firebase.firestore


        getSubscribers()


        return root
    }

    private fun getSubscribers() {
        database.collection("subscribers").get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    val subscriber = result.toObjects(Participant::class.java)[0]
                    val isPaid = subscriber.centerInfo.formation?.get(0)?.paid
                    if (isPaid!!) {

                    }
                }

            }.addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
                Toast.makeText(requireContext(), "${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}