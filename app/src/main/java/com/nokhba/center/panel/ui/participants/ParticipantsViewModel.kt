package com.nokhba.center.panel.ui.participants

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nokhba.center.panel.model.Name
import com.nokhba.center.panel.model.Participant
import com.nokhba.center.panel.model.UserInfo

class ParticipantsViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var database: FirebaseFirestore
    private val _text = MutableLiveData<String>().apply {
        value = "This is add Fragment"
    }
    val text: LiveData<String> = _text


    fun getSubscribers(): List<Participant> {
        val listSubscribers = mutableListOf<Participant>()
        database = Firebase.firestore
        database.collection("subscribers").get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    val user = result.toObjects(Participant::class.java)
                    return@addOnSuccessListener listSubscribers.add(
                        result.size(), Participant(
                            UserInfo(Name(user[result.size()].userInfo.name.first))
                        )
                    )
                }

            }.addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
        return emptyList()
    }
}