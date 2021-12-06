package com.rishik.sahayak.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rishik.sahayak.domain.User
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private var database =
        FirebaseDatabase.getInstance("https://sahayak-ad1ed-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("users")

    private val _loginFailed = MutableLiveData(false)
    val loginFailed: LiveData<Boolean> get() = _loginFailed

    private val _blindUsers = MutableLiveData<Long>(0)
    val blindUsers: LiveData<Long> get() = _blindUsers

    private val _volunteers = MutableLiveData<Long>(0)
    val volunteers: LiveData<Long> get() = _volunteers

    private val _navigateTo = MutableLiveData<String>(null)
    val navigateTo : LiveData<String> get() = _navigateTo

    init {
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

    fun updateNavigation(arg: Int) {
        if(arg == 1)
            _navigateTo.value = "blind"
        else
            _navigateTo.value = "volunteer"
    }

    fun updateDatabase(account: GoogleSignInAccount, userType: String) {
        val user = User(account.email!!, account.displayName!!)

        database.child(userType).child(account.id!!).setValue(user).addOnFailureListener {
            _loginFailed.value = true
        }

        //delete if any duplicate value from db
        if (userType == "volunteer") {
            database.child("blind").child(account.id!!).removeValue()
        } else {
            database.child("volunteer").child(account.id!!).removeValue()
        }
    }

    fun updateLoginFailed() {
        _loginFailed.value = false
    }
}