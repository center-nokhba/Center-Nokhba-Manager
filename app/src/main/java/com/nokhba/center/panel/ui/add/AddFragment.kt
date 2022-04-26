package com.nokhba.center.panel.ui.add

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.card.MaterialCardView
import com.nokhba.center.panel.R
import com.nokhba.center.panel.databinding.FragmentAddBinding
import com.nokhba.center.panel.ui.participants.add.AddParticipant

class AddFragment : DialogFragment(), View.OnClickListener {

    companion object {
        fun newInstance() = AddFragment()
    }

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var addParticipant: MaterialCardView
    private lateinit var addStudent: MaterialCardView
    private lateinit var addFormation: MaterialCardView
    private lateinit var addClass: MaterialCardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val root = layoutInflater.inflate(R.layout.fragment_add, null, false)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(root)
            .setPositiveButton(null) { _, _ -> }
            .create()

        addParticipant = root.findViewById(R.id.add_participant)
        addParticipant.setOnClickListener(this)

        addStudent = root.findViewById(R.id.add_student)
        addStudent.setOnClickListener(this)

        addFormation = root.findViewById(R.id.add_formation)
        addFormation.setOnClickListener(this)

        addClass = root.findViewById(R.id.add_class)
        addClass.setOnClickListener(this)

        return dialog
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.add_participant -> {
                startActivity(Intent(requireContext(), AddParticipant::class.java))
                dismiss()
            }
            R.id.add_student -> {
            }
            R.id.add_formation -> {
            }
            R.id.add_class -> {
            }
            else -> Log.d(this.tag, "onClick: ")

        }
    }
}