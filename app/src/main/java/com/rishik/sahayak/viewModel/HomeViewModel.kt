package com.rishik.sahayak.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private var database =
        FirebaseDatabase.getInstance("https://sahayak-ad1ed-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("users")

    private val _blindUsers = MutableLiveData<Long>(0)
    val blindUsers: LiveData<Long> get() = _blindUsers

    private val _volunteers = MutableLiveData<Long>(0)
    val volunteers: LiveData<Long> get() = _volunteers

    init {
        updateUserCount()
    }

    private fun updateUserCount() {
        viewModelScope.launch {
            database.child("blind").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _blindUsers.value = snapshot.childrenCount
                }

                override fun onCancelled(error: DatabaseError) {}
            })

            database.child("volunteer").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    _volunteers.value = snapshot.childrenCount
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }
    }
}