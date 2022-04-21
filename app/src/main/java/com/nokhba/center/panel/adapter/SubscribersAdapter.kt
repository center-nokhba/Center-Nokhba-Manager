package com.nokhba.center.panel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.nokhba.center.panel.R
import com.nokhba.center.panel.databinding.SubscriberBinding
import com.nokhba.center.panel.model.Subscriber
import java.text.SimpleDateFormat
import java.util.*

class SubscribersAdapter(options: FirestoreRecyclerOptions<Subscriber>) :
    FirestoreRecyclerAdapter<Subscriber, SubscribersAdapter.SubscribersViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscribersViewHolder =
        SubscribersViewHolder(SubscribersViewHolder.from(parent))

    override fun onBindViewHolder(holder: SubscribersViewHolder, position: Int, model: Subscriber) {
        val nameComplete = "${model.userInfo.name.first} ${model.userInfo.name.last}"
        holder.binding.nameComplete.text = nameComplete
        holder.binding.mobileNumber.text = model.userInfo.mobileNumber
        holder.binding.registrationDate.text =
            formatDate(model.centerInfo.firstRegistration!!.toDate())

        if (model.centerInfo.formation!![position]!!.paid) {
            ContextCompat.getDrawable(holder.itemView.context, R.drawable.circle_shape_paid)
        }
    }

    private fun formatDate(date: Date): String =
        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)

    class SubscribersViewHolder internal constructor(val binding: SubscriberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): SubscriberBinding {
                val inflater = LayoutInflater.from(parent.context)
                return SubscriberBinding.inflate(inflater, parent, false)
            }
        }
    }
}