package com.rishik.sahayak.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rishik.sahayak.databinding.ActivityMainBinding
import com.rishik.sahayak.domain.User
import com.rishik.sahayak.util.SavedPreference
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityMainBinding

    private val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    private val requestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!isPermissionGranted()) {
            askPermissions()
        }

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

    private fun askPermissions() {
        ActivityCompat.requestPermissions(this, permissions, requestCode)
    }

    private fun isPermissionGranted(): Boolean {
        permissions.forEach {
            if(ActivityCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }
}