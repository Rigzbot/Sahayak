package com.rishik.sahayak.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rishik.sahayak.R
import com.rishik.sahayak.databinding.ActivityLoginBinding
import com.rishik.sahayak.databinding.ActivityMainBinding
import com.rishik.sahayak.domain.User
import com.rishik.sahayak.util.SavedPreference

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        val userType: String?
        if (extras != null) {
            userType = extras.getString("userType")
            database = FirebaseDatabase.getInstance("https://sahayak-ad1ed-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")

            val name = SavedPreference.getUsername(this)
            val email = SavedPreference.getEmail(this)
            val id = SavedPreference.getId(this)

            val user = User(email!!, name!!, userType!!)

            database.child(id!!).setValue(user).addOnFailureListener {
                Toast.makeText(this, "Login Failed, please try again!", Toast.LENGTH_SHORT).show()
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}