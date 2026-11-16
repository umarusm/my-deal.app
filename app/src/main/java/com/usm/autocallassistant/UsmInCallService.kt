package com.usm.autocallassistant

import android.os.Handler
import android.os.Looper
import android.telecom.Call
import android.telecom.InCallService
import android.util.Log

class UsmInCallService : InCallService() {

    companion object {
        private const val TAG = "UsmInCallService"
    }

    override fun onCallAdded(call: Call) {
        super.onCallAdded(call)
        Log.d(TAG, "New call added. State: ${call.state}")

        // اگر کال آ رہی ہو (RINGING)
        if (call.state == Call.STATE_RINGING) {
            // 5 سیکنڈ کے بعد کال اٹھانے کا ٹائمر
            Handler(Looper.getMainLooper()).postDelayed({
                try {
                    // کال کا جواب دیں
                    call.answer(android.telecom.VideoProfile.STATE_AUDIO_ONLY)
                    
                    // آڈیو کو اسپیکر پر شفٹ کریں (درست طریقہ)
                    call.setAudioRoute(Call.AUDIO_ROUTE_SPEAKER)
                    Log.d(TAG, "Call answered and routed to speaker automatically.")
                } catch (e: Exception) {
                    Log.e(TAG, "Error answering call: ${e.message}")
                }
            }, 5000) // 5 سیکنڈ
        }
    }

    override fun onCallRemoved(call: Call) {
        super.onCallRemoved(call)
        Log.d(TAG, "Call removed.")
    }
}
