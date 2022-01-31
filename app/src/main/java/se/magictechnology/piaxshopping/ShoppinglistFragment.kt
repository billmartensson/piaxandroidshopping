package se.magictechnology.piaxshopping

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ShoppinglistFragment : Fragment() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
            for(snapchild in it.children) {
                var tempshop = snapchild.getValue<Shopitem>()

                Log.i("piaxdebug", tempshop!!.shoptitle!!)
            }
        }
    }

}