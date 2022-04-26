package com.nokhba.center.panel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.nokhba.center.panel.R
import com.nokhba.center.panel.databinding.ParticipantBinding
import com.nokhba.center.panel.model.Participant
import com.nokhba.center.panel.utils.DEFAULT_DATE
import com.nokhba.center.panel.utils.format

class ParticipantsAdapter(options: FirestoreRecyclerOptions<Participant>) :
    FirestoreRecyclerAdapter<Participant, ParticipantsAdapter.ParticipantsViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantsViewHolder =
        ParticipantsViewHolder(ParticipantsViewHolder.from(parent))

    override fun onBindViewHolder(holder: ParticipantsViewHolder, position: Int, model: Participant) {

        holder.binding.nameComplete.text =
            model.userInfo.name.last.plus(" ").plus(model.userInfo.name.first)
        holder.binding.mobileNumber.text = model.userInfo.mobileNumber

        val date = model.centerInfo.firstRegistration!!.toDate().format(DEFAULT_DATE)
        holder.binding.registrationDate.text = date


        for (i in model.centerInfo.formation!!.indices) {
            if (model.centerInfo.formation[i]?.paid!!) {
                val drawable =
                    ContextCompat.getDrawable(holder.itemView.context, R.drawable.circle_shape_paid)
                holder.binding.paid.setImageDrawable(drawable)
            } else {
                val drawable = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.circle_shape_unpaid
                )
                holder.binding.paid.setImageDrawable(drawable)
            }
        }
    }

    class ParticipantsViewHolder internal constructor(val binding: ParticipantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ParticipantBinding {
                val inflater = LayoutInflater.from(parent.context)
                return ParticipantBinding.inflate(inflater, parent, false)
            }
        }
    }
}