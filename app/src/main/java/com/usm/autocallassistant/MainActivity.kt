package com.usm.autocallassistant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telecom.TelecomManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ऐप को डिफ़ॉल्ट डायलर बनाने की अनुमति मांगें
        requestDefaultDialer()
    }

    private fun requestDefaultDialer() {
        // यहाँ TELECOM_MANAGER की जगह TELECOM_SERVICE का उपयोग किया गया है
        val telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        if (packageName != telecomManager.defaultDialerPackage) {
            val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER).apply {
                putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
            }
            startActivity(intent)
        }
    }
}
