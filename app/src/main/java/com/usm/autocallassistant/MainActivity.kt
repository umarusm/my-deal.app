package com.usm.autocallassistant

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.telecom.TelecomManager
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ڈارک اور ہائی اسٹائل بیک گراؤنڈ
        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
            setPadding(48, 80, 48, 48)
            setBackgroundColor(Color.parseColor("#0B0F19")) // گہرا سائبر پنک ڈارک کلر
        }

        // ایپ کا ٹائٹل یا لوگو اسٹائل
        val titleText = TextView(this).apply {
            text = "⚡ USM AUTO CALL ⚡"
            textSize = 22f
            setTextColor(Color.parseColor("#FF9800")) // بولڈ اورنج کلر
            gravity = Gravity.CENTER
            setPadding(0, 0, 0, 40)
        }

        // اسٹیٹس کارڈ (گلاس اور ڈارک لک)
        statusText = TextView(this).apply {
            text = "Status: Checking permissions..."
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

        // اسٹائلش ایکشن بٹن
        val actionButton = Button(this).apply {
            text = "🛡️ Set as Default Phone App"
            textSize = 15f
            setTextColor(Color.WHITE)
            
            val btnBg = GradientDrawable().apply {
                setColor(Color.parseColor("#FF6F00")) // پرکشش اورنج شیڈ
                cornerRadius = 24f
            }
            background = btnBg
            setPadding(32, 28, 32, 28)
            
            setOnClickListener {
                requestDefaultDialer()
            }
        }

        // نوٹ یا ڈسکرپشن
        val noteText = TextView(this).apply {
            text = "ℹ️ Prototype: Automatically answers incoming calls after 5 seconds and routes audio to speaker."
            textSize = 13f
            setTextColor(Color.parseColor("#8B949E"))
            gravity = Gravity.CENTER
            setPadding(0, 40, 0, 0)
        }

        // ویوز کو ترتیب سے جوڑنا
        rootLayout.addView(titleText)
        rootLayout.addView(statusText)
        
        // بٹن کے اوپر مارجن دینے کے لیے
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

    private fun requestDefaultDialer() {
        val telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        if (packageName != telecomManager.defaultDialerPackage) {
            val intent = Intent(TelecomManager.ACTION_CHANGE_DEFAULT_DIALER).apply {
                putExtra(TelecomManager.EXTRA_CHANGE_DEFAULT_DIALER_PACKAGE_NAME, packageName)
            }
            startActivity(intent)
        } else {
            Toast.makeText(this, "App is already the default dialer!", Toast.LENGTH_SHORT).show()
            updateRoleStatus()
        }
    }

    private fun updateRoleStatus() {
        val telecomManager = getSystemService(Context.TELECOM_SERVICE) as TelecomManager
        val isDefault = packageName == telecomManager.defaultDialerPackage
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
