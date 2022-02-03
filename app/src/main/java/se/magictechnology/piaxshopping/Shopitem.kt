package se.magictechnology.piaxshopping

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Shopitem(val shoptitle: String? = null, val shopamount: Int? = null) {
    // Null default values create a no-argument default constructor, which is needed
    // for deserialization from a DataSnapshot.

    var fbid = ""
    var shopdone = false

}
