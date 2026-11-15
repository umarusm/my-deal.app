package com.usm.autocallassistant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telecom.TelecomManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // बिना XML फाइल के सीधे कोड से लेआउट तैयार करना
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 60, 40, 40)
        }

        val title = TextView(this).apply {
            text = "USM Auto Call"
            textSize = 24f
        }

        status = TextView(this).apply {
            text = "Auto-answer setup: not enabled"
            textSize = 16f
            setPadding(0, 32, 0, 32)
        }

        val roleButton = Button(this).apply {
            text = "Set as default phone app"
            setOnClickListener {
                requestDefaultDialer()
            }
        }

        val note = TextView(this).apply {
            text = "Prototype: answers incoming calls after 5 seconds and routes audio to speaker."
            textSize = 14f
            setPadding(0, 24, 0, 0)
        }

        // सभी कॉम्पोनेंट्स को लेआउट में जोड़ना
        layout.addView(title)
        layout.addView(status)
        layout.addView(roleButton)
        layout.addView(note)

        setContentView(layout)
        updateRoleStatus()
    }

    private fun requestDefaultDialer() {
        val telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        if (packageName != telecomManager.defaultDialerPackage) {
            val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER).apply {
                putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "App is already the default dialer", Toast.LENGTH_SHORT).show()
            updateRoleStatus()
        }
    }

    private fun updateRoleStatus() {
        val telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        val isDefault = packageName == telecomManager.defaultDialerPackage
        status.text = if (isDefault) "Default phone app: enabled" else "Default phone app: not enabled"
    }

    override fun onResume() {
        super.onResume()
        updateRoleStatus()
    }
}
