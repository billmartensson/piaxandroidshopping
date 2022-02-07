package se.magictechnology.piaxshopping

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.getValue

class ShoppinglistViewModel : ViewModel() {
    lateinit var database: DatabaseReference

    //var shopitems = mutableListOf<Shopitem>()

    val shopitems: MutableLiveData<List<Shopitem>> by lazy {
        MutableLiveData<List<Shopitem>>()
    }

    fun loadShopping()
    {
        Log.i("piaxdebug", "** LOAD SHOPPING **")
        database.child("androidshopping").get().addOnSuccessListener {

            var tempShopList = mutableListOf<Shopitem>()

            for(snapchild in it.children) {
                var tempshop = snapchild.getValue<Shopitem>()!!
                tempshop.fbid = snapchild.key!!
                Log.i("piaxdebug", tempshop!!.shoptitle!!)

                tempShopList.add(tempshop)

            }
            shopitems.value = tempShopList
        }
    }

    fun toggleDone(itemnumber : Int)
    {
        var currentitem = shopitems.value!![itemnumber]

        if(currentitem.shopdone == true)
        {
            currentitem.shopdone = false
        } else {
            currentitem.shopdone = true
        }

        val shopref = database.child("androidshopping").child(currentitem.fbid)
        shopref.setValue(currentitem)

        loadShopping()
    }

    fun addShopping(itemtitle : String, itemamount : Int)
    {
        val saveshop = Shopitem(itemtitle, itemamount)

        val shopref = database.child("androidshopping").push()
        shopref.setValue(saveshop)

        loadShopping()
    }
}