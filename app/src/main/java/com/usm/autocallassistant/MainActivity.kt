package com.usm.autocallassistant

import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private val REQUEST_DIALER_ROLE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(48, 80, 48, 48)
            setBackgroundColor(Color.parseColor("#0B0F19"))
        }

        val titleText = TextView(this).apply {
            text = "⚡ USM AUTO CALL ⚡"
            textSize = 22f
            setTextColor(Color.parseColor("#FF9800"))
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 40)
        }

        statusText = TextView(this).apply {
            text = "Status: Checking status..."
            textSize = 15f
            setTextColor(Color.parseColor("#E0E0E0"))
            gravity = Gravity.CENTER
            
            val bg = GradientDrawable().apply {
                setColor(Color.parseColor("#161B22"))
                cornerRadius = 24f
                setStroke(2, Color.parseColor("#30363D"))
            }
            background = bg
            setPadding(32, 36, 32, 36)
        }

        val actionButton = Button(this).apply {
            text = "🛡️ SET AS DEFAULT PHONE APP"
            textSize = 15f
            setTextColor(Color.WHITE)
            
            val btnBg = GradientDrawable().apply {
                setColor(Color.parseColor("#FF6F00"))
                cornerRadius = 24f
            }
            background = btnBg
            setPadding(32, 28, 32, 28)
            
            setOnClickListener {
                requestDefaultDialerRole()
            }
        }

        val noteText = TextView(this).apply {
            text = "ℹ️ Prototype: Automatically answers incoming calls after 5 seconds and routes audio to speaker."
            textSize = 13f
            setTextColor(Color.parseColor("#8B949E"))
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 0)
        }

        rootLayout.addView(titleText)
        rootLayout.addView(statusText)
        
        val btnParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(0, 48, 0, 0)
        }
        rootLayout.addView(actionButton, btnParams)
        rootLayout.addView(noteText)

        setContentView(rootLayout)
        updateRoleStatus()
    }

    private fun requestDefaultDialerRole() {
        val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
        if (roleManager.isRoleAvailable(RoleManager.ROLE_DIALER)) {
            if (!roleManager.isRoleHeld(RoleManager.ROLE_DIALER)) {
                val intent = roleManager.createRequestRoleIntent(RoleManager.ROLE_DIALER)
                startActivityForResult(intent, REQUEST_DIALER_ROLE)
            } else {
                Toast.makeText(this, "App is already the default dialer!", Toast.LENGTH_SHORT).show()
                updateRoleStatus()
            }
        } else {
            Toast.makeText(this, "Role dialer not available on this device", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_DIALER_ROLE) {
            updateRoleStatus()
        }
    }

    private fun updateRoleStatus() {
        val roleManager = getSystemService(Context.ROLE_SERVICE) as RoleManager
        val isDefault = roleManager.isRoleHeld(RoleManager.ROLE_DIALER)
        statusText.text = if (isDefault) {
            "✅ Default Phone App: Active"
        } else {
            "⚠️ Default Phone App: Not Enabled"
        }
    }

    override fun onResume() {
        super.onResume()
        updateRoleStatus()
    }
}
