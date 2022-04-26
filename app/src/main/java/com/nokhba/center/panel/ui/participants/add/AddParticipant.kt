package com.nokhba.center.panel.ui.participants.add

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.nokhba.center.panel.R
import com.nokhba.center.panel.databinding.AddParticipantBinding
import com.nokhba.center.panel.model.*
import com.nokhba.center.panel.utils.PARTICIPANTS
import com.nokhba.center.panel.utils.format
import com.nokhba.center.panel.utils.showSnack

class AddParticipant : AppCompatActivity() {

    private lateinit var binding: AddParticipantBinding

    private lateinit var viewModel: AddParticipantViewModel

    private lateinit var mobileNumberField: TextInputLayout
    private lateinit var firstNameField: TextInputLayout
    private lateinit var lastNameField: TextInputLayout
    private lateinit var genreDropdown: TextInputLayout
    private lateinit var addressField: TextInputLayout
    private lateinit var citiesDropdown: TextInputLayout
    private lateinit var birthdayField: TextInputLayout
    private lateinit var icnField: TextInputLayout
    private lateinit var serviceDropdown: TextInputLayout

    private lateinit var branchDropdown: TextInputLayout
    private lateinit var schoolDropdown: TextInputLayout
    private lateinit var groupDropdown: TextInputLayout

    private lateinit var studentSwitch: SwitchMaterial
    private lateinit var paidSwitch: SwitchMaterial

    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[AddParticipantViewModel::class.java]
        binding = AddParticipantBinding.inflate(layoutInflater).apply {
            setContentView(root)
            this@AddParticipant.mobileNumberField = mobileNumberField
            this@AddParticipant.firstNameField = firstNameField
            this@AddParticipant.lastNameField = lastNameField
            this@AddParticipant.genreDropdown = genreDropdown
            this@AddParticipant.addressField = addressField
            this@AddParticipant.citiesDropdown = citiesDropdown
            this@AddParticipant.birthdayField = birthdayField
            this@AddParticipant.icnField = icnField
            this@AddParticipant.serviceDropdown = serviceDropdown
            this@AddParticipant.branchDropdown = branchDropdown
            this@AddParticipant.schoolDropdown = schoolDropdown
            this@AddParticipant.groupDropdown = groupDropdown

            this@AddParticipant.studentSwitch = studentSwitch
            this@AddParticipant.paidSwitch = paidSwitch
        }

        db = Firebase.firestore
        bind()

    }

    private fun bind() {
        studentSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                branchDropdown.visibility = View.VISIBLE
                schoolDropdown.visibility = View.VISIBLE
            } else {
                branchDropdown.visibility = View.INVISIBLE
                schoolDropdown.visibility = View.INVISIBLE
            }
        }

        genreDropdown.bind(getStringArray(R.array.genre))
        citiesDropdown.bind(getStringArray(R.array.cities))
        serviceDropdown.bind(getStringArray(R.array.services))
        groupDropdown.bind(getStringArray(R.array.groups))
    }

    private fun TextInputLayout.bind(listData: Array<String>) {
        val adapter =
            ArrayAdapter(this@AddParticipant, android.R.layout.simple_list_item_1, listData)
        (this.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun getStringArray(id: Int) = resources.getStringArray(id)


    private fun addSubscriber() {

        val mobileNumber = mobileNumberField.editText?.text.toString().trim()
        val firstName = firstNameField.editText?.text.toString().trim()
        val lastName = lastNameField.editText?.text.toString().trim()
        val genre = genreDropdown.editText?.text.toString().trim()
        val address = addressField.editText?.text.toString().trim()
        val birthday = birthdayField.editText?.text.toString().trim()
        val icn = icnField.editText?.text.toString().trim()

        val city = citiesDropdown.editText?.text.toString().trim()

        val branch = branchDropdown.editText?.text.toString().trim()
        val school = schoolDropdown.editText?.text.toString().trim()
        val group = groupDropdown.editText?.text.toString().trim()
        val name = serviceDropdown.editText?.text.toString().trim()


        if (!TextUtils.isEmpty(firstName) &&
            !TextUtils.isEmpty(lastName) &&
            !TextUtils.isEmpty(address) &&
            !TextUtils.isEmpty(birthday) &&
            !TextUtils.isEmpty(icn) &&
            !TextUtils.isEmpty(mobileNumber) &&
            !TextUtils.isEmpty(genre) &&
            !TextUtils.isEmpty(name) &&
            !TextUtils.isEmpty(group)
        ) {

            val userInfo = UserInfo(
                name = Name(firstName, lastName),
                birth = birthday.format(),
                address = address,
                city = city,
                mobileNumber = mobileNumber,
                icn = icn,
                genre = genre
            )

            val service = Service(name = name, paid = paidSwitch.isChecked, group = group)

            val centerInfo = CenterInfo(listOf(service))

            val isStudent = studentSwitch.isChecked
            val studyInfo = StudyInfo(isStudent = isStudent, branch = branch, school = school)

            val subscriber =
                Participant(userInfo = userInfo, centerInfo = centerInfo, studyInfo = studyInfo)

            db.collection(PARTICIPANTS).document(icn).set(subscriber).addOnSuccessListener {
                binding.root.showSnack("Subscriber added successfully :D.").animationMode =
                    BaseTransientBottomBar.ANIMATION_MODE_SLIDE
                init()
            }.addOnFailureListener {
                binding.root.showSnack("Failed to add Subscriber :-(!").animationMode =
                    BaseTransientBottomBar.ANIMATION_MODE_SLIDE
            }
        } else {
            firstNameField.error = "Enter your first name!"
            lastNameField.error = "Enter your last name!"
            addressField.error = "Enter your address!"
            birthdayField.error = "Enter your birthday!"
            icnField.error = "Enter your icn!"
            mobileNumberField.error = "Enter your mobile number!"
            citiesDropdown.error = "Enter your City!"
            genreDropdown.error = "Enter your genre!"
            serviceDropdown.error = "Enter your service!"
            groupDropdown.error = "Enter your group!"
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.submit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.submit_subscriber -> {
                addSubscriber()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        mobileNumberField.editText?.text = null
        firstNameField.editText?.text = null
        lastNameField.editText?.text = null
        genreDropdown.editText?.text = null
        addressField.editText?.text = null
        citiesDropdown.editText?.text = null
        birthdayField.editText?.text = null
        icnField.editText?.text = null
        branchDropdown.editText?.text = null
        schoolDropdown.editText?.text = null
        serviceDropdown.editText?.text = null
        groupDropdown.editText?.text = null
        studentSwitch.isChecked = false
        paidSwitch.isChecked = false
    }
}