package com.usm.autocallassistant

import android.telecom.Call
import android.telecom.CallAudioState
import android.telecom.InCallService
import android.telecom.VideoProfile
import android.os.Handler
import android.os.Looper

class UsmInCallService : InCallService() {

    override fun onCallAdded(call: Call) {
        super.onCallAdded(call)

        Handler(Looper.getMainLooper()).postDelayed({

            if (call.state == Call.STATE_RINGING) {
                call.answer(VideoProfile.STATE_AUDIO_ONLY)

                setAudioRoute(
                    CallAudioState.ROUTE_SPEAKER
                )
            }

        }, 5000)
    }
}
