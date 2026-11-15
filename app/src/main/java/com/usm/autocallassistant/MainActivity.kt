package com.usm.autocallassistant

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telecom.TelecomManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ایپ کو ڈیفال트 ڈائلر بنانے کی درخواست
        requestDefaultDialer()
    }

    private fun requestDefaultDialer() {
        val telecomManager = getSystemService(Context.TELECOM_MANAGER) as TelecomManager
        if (packageName != telecomManager.defaultDialerPackage) {
            val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER).apply {
                putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
            }
            startActivity(intent)
        }
    }
}
