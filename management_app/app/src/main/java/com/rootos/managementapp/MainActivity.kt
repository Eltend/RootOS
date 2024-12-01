package com.rootos.managementapp

import android.os.Bundle
import android.util.Log

import android.widget.Button

import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rootos.managementapp.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {


private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMainBinding.inflate(layoutInflater)
     setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        findViewById<Button>(R.id.home_reboot)
            .setOnClickListener {
                //Log.d("BUTTONS", "User tapped the Supabutton")

                Runtime.getRuntime().exec("su -c reboot")

            }
        findViewById<Button>(R.id.home_reboot_rec)
            .setOnClickListener {
                //Log.d("BUTTONS", "User tapped the Supabutton")

                Runtime.getRuntime().exec("su -c reboot recovery")

            }
        findViewById<Button>(R.id.home_reboot_bot)
            .setOnClickListener {
                //Log.d("BUTTONS", "User tapped the Supabutton")

                Runtime.getRuntime().exec("su -c reboot bootloader")
            }

        findViewById<Button>(R.id.home_whoami)
            .setOnClickListener {
                //Log.d("BUTTONS", "User tapped the Supabutton")

            }




    }
}
