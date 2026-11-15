package com.usm.autocallassistant

import android.os.Bundle
import android.widget.TextView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(32, 48, 32, 32)

        val title = TextView(this)
        title.text = "USM Auto Call Assistant"
        title.textSize = 24f

        val status = TextView(this)
        status.text = "Auto Call Assistant is ready"
        status.textSize = 18f

        layout.addView(title)
        layout.addView(status)

        setContentView(layout)
    }
}
