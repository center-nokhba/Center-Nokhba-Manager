package com.nokhba.center.panel.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nokhba.center.panel.adapter.SubscribersAdapter
import com.nokhba.center.panel.databinding.FragmentDashboardBinding
import com.nokhba.center.panel.model.Subscriber

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var recyclerView: RecyclerView
    private lateinit var database: FirebaseFirestore
    private lateinit var adapter: SubscribersAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val root: View = binding.root

        database = Firebase.firestore

        val query = FirebaseFirestore.getInstance().collection("subscribers")

        val options = FirestoreRecyclerOptions.Builder<Subscriber>()
            .setQuery(query, Subscriber::class.java)
            .build()

        adapter = SubscribersAdapter(options)

        recyclerView = binding.recyclerSubscribers
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        getSubscribers()


        return root
    }

    private fun getSubscribers() {
        database.collection("subscribers").get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    Toast.makeText(
                        requireContext(),
                        "${document["first_name"]}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }.addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
                Toast.makeText(requireContext(), "${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}