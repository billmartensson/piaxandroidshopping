package se.magictechnology.piaxshopping

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class ShoppinglistFragment : Fragment() {

    var shopadapter = ShoppingAdapter()

    val model : ShoppinglistViewModel by activityViewModels()

    lateinit var loadingview : ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shopadapter.shopfrag = this

        model.database = Firebase.database("https://piax-acf23-default-rtdb.europe-west1.firebasedatabase.app").reference

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

        loadingview = view.findViewById(R.id.loadingView)

        var recview = view.findViewById<RecyclerView>(R.id.shoppinglistRV)

        recview.layoutManager = LinearLayoutManager(requireContext())
        recview.adapter = shopadapter

        val shopObserver = Observer<List<Shopitem>> {
            shopadapter.notifyDataSetChanged()
            loadingview.visibility = View.GONE
        }

        model.shopitems.observe(this, shopObserver)

        loadingview.visibility = View.VISIBLE
        model.loadShopping()


        view.findViewById<Button>(R.id.shoppinglistAddBtn).setOnClickListener {
            val shoptitle = view.findViewById<EditText>(R.id.shoppinglistAddTitleET).text.toString()
            val shopamount = view.findViewById<EditText>(R.id.shoppinglistAddAmountET).text.toString()

            // TODO: Kolla så det är siffra
            if(shopamount.toIntOrNull() == null)
            {
                // VISA ERROR ALERT
            } else {
                val shopamountNumber = shopamount.toInt()

                loadingview.visibility = View.VISIBLE
                model.addShopping(shoptitle, shopamountNumber)
            }

        }
    }

    fun clickrow(rownumber : Int)
    {
        loadingview.visibility = View.VISIBLE
        model.toggleDone(rownumber)
    }

}