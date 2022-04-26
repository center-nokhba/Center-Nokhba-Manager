package com.nokhba.center.panel.ui.participants

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nokhba.center.panel.R
import com.nokhba.center.panel.adapter.ParticipantsAdapter
import com.nokhba.center.panel.databinding.FragmentParticipantBinding
import com.nokhba.center.panel.model.Participant
import com.nokhba.center.panel.ui.participants.add.AddParticipant
import com.nokhba.center.panel.utils.PARTICIPANTS
import com.nokhba.center.panel.utils.addIcon

class ParticipantsFragment : Fragment() {

    private var _binding: FragmentParticipantBinding? = null

    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var database: FirebaseFirestore
    private lateinit var adapter: ParticipantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val subscribersViewModel =
            ViewModelProvider(this)[ParticipantsViewModel::class.java]

        _binding = FragmentParticipantBinding.inflate(inflater, container, false)

        val root: View = binding.root

        database = Firebase.firestore

        val query = FirebaseFirestore.getInstance().collection(PARTICIPANTS)

        val options = FirestoreRecyclerOptions.Builder<Participant>()
            .setQuery(query, Participant::class.java)
            .build()

        adapter = ParticipantsAdapter(options)

        recyclerView = binding.recyclerSubscribers
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        return root
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