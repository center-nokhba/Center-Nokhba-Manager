package com.nokhba.center.panel.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Participant constructor(

    @JvmField
    @PropertyName("user_info")
    var userInfo: UserInfo = UserInfo(),

    @JvmField
    @PropertyName("study_info")
    var studyInfo: StudyInfo = StudyInfo(),

    @JvmField
    @PropertyName("center_info")
    val centerInfo: CenterInfo = CenterInfo()
)

data class Name(
    val first: String? = "Center",
    val last: String? = "Nokhba"
)

data class UserInfo(
    val name: Name = Name(),
    var birth: String? = null,
    var address: String? = null,

    @JvmField
    @PropertyName("mobile_number")
    var mobileNumber: String? = null,

    var icn: String? = null,
    var genre: String? = null,
    var city: String? = null
)

data class StudyInfo(
    val isStudent: Boolean = false,
    val school: String? = null,
    val branch: String? = null
)

data class CenterInfo(

    val formation: List<Service?>? = null,

    @JvmField
    @ServerTimestamp
    @PropertyName("first_registration")
    var firstRegistration: Timestamp? = null
)

data class Service(

    val name: String? = null,
    val paid: Boolean = false,
    val group: String? = null,

    @JvmField
    @ServerTimestamp
    @PropertyName("registration_date")
    var registrationDate: String? = null
)

