package se.magictechnology.piaxshopping

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ShoppinglistFragment : Fragment() {

    private lateinit var database: DatabaseReference

    var shopadapter = ShoppingAdapter()
    var shopitems = mutableListOf<Shopitem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shopadapter.shopfrag = this
        database = Firebase.database("https://piax-acf23-default-rtdb.europe-west1.firebasedatabase.app").reference

        loadShopping()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shoppinglist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recview = view.findViewById<RecyclerView>(R.id.shoppinglistRV)

        recview.layoutManager = LinearLayoutManager(requireContext())
        recview.adapter = shopadapter

        view.findViewById<Button>(R.id.shoppinglistAddBtn).setOnClickListener {
            val shoptitle = view.findViewById<EditText>(R.id.shoppinglistAddTitleET).text.toString()
            val shopamount = view.findViewById<EditText>(R.id.shoppinglistAddAmountET).text.toString()

            // TODO: Kolla så det är siffra
            val saveshop = Shopitem(shoptitle, shopamount.toInt())

            val shopref = database.child("androidshopping").push()
            shopref.setValue(saveshop)

            loadShopping()
        }
    }

    fun loadShopping()
    {
        Log.i("piaxdebug", "** LOAD SHOPPING **")
        database.child("androidshopping").get().addOnSuccessListener {
            shopitems.clear()
            for(snapchild in it.children) {
                var tempshop = snapchild.getValue<Shopitem>()!!
                tempshop.fbid = snapchild.key!!
                Log.i("piaxdebug", tempshop!!.shoptitle!!)

                shopitems.add(tempshop)

            }
            shopadapter.notifyDataSetChanged()
        }
    }

    fun clickrow(rownumber : Int)
    {
        var currentitem = shopitems[rownumber]

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

}