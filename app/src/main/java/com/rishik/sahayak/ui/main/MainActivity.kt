package com.rishik.sahayak.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rishik.sahayak.databinding.ActivityMainBinding
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.rishik.sahayak.R
import android.content.Intent
import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.rishik.sahayak.databinding.NavHeaderNavBinding
import com.rishik.sahayak.ui.login.LoginActivity
import com.rishik.sahayak.util.SavedPreference


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    private val requestCode = 1

    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!isPermissionGranted()) {
            askPermissions()
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNav.toolbar)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_nav)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setupViews()
    }

    private fun setupViews() {
        binding.contactUsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:" + getString(R.string.email_address)) // only email apps should handle this
            startActivity(intent)
        }
        binding.shareButton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey check out this amazing app I Found at: https://github.com/Rigzbot/Sahayak"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
        binding.logoutButton.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        val viewHeader = binding.navView.getHeaderView(0)
        val navBinding: NavHeaderNavBinding = NavHeaderNavBinding.bind(viewHeader)
        navBinding.userName.text = SavedPreference.getUsername(this)
        navBinding.userEmail.text = SavedPreference.getEmail(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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