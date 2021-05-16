package com.afrakhteh.dogsapp.view.activities

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.afrakhteh.dogsapp.R
import com.afrakhteh.dogsapp.utils.Constant
import com.afrakhteh.dogsapp.view.fragments.DetailFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun checkSmsPermission() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.SEND_SMS
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this, android.Manifest.permission.SEND_SMS
                )
            ) {

                AlertDialog.Builder(this)
                    .setTitle("Send SMS permission")
                    .setMessage("This app requires access to send an SMS ")
                    .setPositiveButton("Ask me") { dialog, which ->
                        requestSmsPermission()
                    }
                    .setNegativeButton("No") { dialog, which ->
                        notifyDetailFragment(false)
                    }
                    .show()

            } else {
                requestSmsPermission()
            }
        } else {
            notifyDetailFragment(true)
        }


    }

    private fun notifyDetailFragment(permissionGranted: Boolean) {
        val activeFragment = fragment.childFragmentManager.primaryNavigationFragment
        if (activeFragment is DetailFragment) {
            activeFragment.onPermissionResult(permissionGranted)
        }

    }

    private fun requestSmsPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.SEND_SMS),
            Constant.PERMISSION_SMS_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constant.PERMISSION_SMS_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    notifyDetailFragment(true)
                } else {
                    notifyDetailFragment(false)
                }
            }
        }
    }
}