# USM Auto Call Assistant (Android prototype)

A starter Android Studio project using Android Telecom's `InCallService`.

## Included
- Requests the Android default dialer role.
- Watches incoming calls through `InCallService`.
- Answers a ringing call after 5 seconds.
- Attempts to route the call to speaker after answering.

## Important limitation
This native-dialer prototype does **not** transmit Android Text-to-Speech audio to the caller. Android does not provide ordinary third-party dialer apps a general-purpose API to inject arbitrary app audio into the cellular call uplink. The English message is therefore not spoken to the remote caller by this starter project.

To make the caller hear an AI voice, use a supported telephony/SIP/VoIP call bridge (or a carrier feature that explicitly supports this), with caller consent and applicable local-law compliance. The bridge can play the prompt and optionally run a constrained conversational agent.

## Build
1. Open this folder in Android Studio (Giraffe or newer; Android Gradle Plugin 8.7.3).
2. Allow Gradle sync.
3. Build > Build APK(s).
4. Install on a test Android phone.
5. Open the app and tap **Set as default phone app**.
6. Test with a second phone. Keep emergency calling available through the device's supported dialer.

## Notes
- Device manufacturers and Android versions may restrict or alter call behavior.
- This is a development prototype, not a production-ready dialer.
- Before production, add an explicit Auto Answer on/off setting, contact allowlist, call-state UI, robust permission handling, accessibility review, privacy policy, and extensive device testing.


## Build APK from an Android phone using GitHub Actions (free tier subject to GitHub limits)

1. Create a **public or private** GitHub repository and upload the contents of this project (not the ZIP file itself).
2. Make sure the workflow file `.github/workflows/android-apk.yml` is included.
3. Open the repository on GitHub, tap **Actions**, select **Build USM Auto Call Assistant APK**, then tap **Run workflow**.
4. When the run finishes with a green check, open that run, scroll to **Artifacts**, and download `USM-Auto-Call-Assistant-debug-apk`.
5. Extract the downloaded artifact ZIP and install `app-debug.apk`. Android may ask you to allow installation from that browser/file manager.

GitHub Actions free usage limits apply. This workflow creates a **debug APK**, not a Play Store release.

Important: this prototype's native dialer can attempt auto-answer and speaker routing, but it does not inject app TTS into the cellular call uplink. The caller will not hear the AI message until a supported telephony/SIP audio bridge is implemented.
