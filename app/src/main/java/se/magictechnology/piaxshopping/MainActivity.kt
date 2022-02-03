package se.magictechnology.piaxshopping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}


/*
private lateinit var database: DatabaseReference

database = Firebase.database("https://piax-acf23-default-rtdb.europe-west1.firebasedatabase.app").reference

        val myRef = database.child("piaxandroid")

        myRef.setValue("Hurra det funkar")

        val shopRef = database.child("androidshopping")

        val buything = Shopitem("Apelsin", 5)

        //shopRef.push().setValue(buything)

        /*
        // Read from the database
        shopRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<Shopitem>()

                Log.d("piaxdebug", "Value is: ${value!!.shoptitle}")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("piaxdebug", "Failed to read value.", error.toException())
            }
        })
*/

        shopRef.get().addOnSuccessListener {

            var myshoppinglist = mutableListOf<Shopitem>()
            for(childsnap in it.children) {
                val someitem = childsnap.getValue<Shopitem>()
                myshoppinglist.add(someitem!!)
                Log.d("piaxdebug", "Get Value is: ${someitem!!.shoptitle}")

            }



        }



 */