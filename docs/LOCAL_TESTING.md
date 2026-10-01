# Local Android runtime testing

Static patch gates are not sufficient for release approval. Every candidate APK must pass a local launch smoke test before its versionCode is removed from `ci/runtime-quarantine.json`.

## Test lab

- SDK root: `C:\AndroidLab\Sdk`
- AVD root: `C:\AndroidLab\Avd`
- JDK 21: `C:\AndroidLab\Jdk21`
- Candidate APKs: `C:\AndroidLab\APKs`
- Accelerated emulator profiles: `VKVideo_API31_Play`, `VKVideo_API35_Play`

The SDK and AVD paths intentionally contain ASCII only because QEMU on Windows can corrupt Cyrillic paths before starting the guest.

## Required smoke test

Start the emulator, wait for `sys.boot_completed=1`, then run:

```powershell
.\scripts\android-smoke-test.ps1 -ApkPath C:\AndroidLab\APKs\candidate.apk
```

The test installs the APK, clears logcat, cold-starts the launcher activity, observes the process for 15 seconds, saves the complete log, and fails if the process dies or Android reports a fatal exception.

To run the accelerated emulator matrix on Android 12 and Android 15:

```powershell
.\scripts\android-smoke-matrix.ps1 -ApkPath C:\AndroidLab\APKs\candidate.apk
```

Android 9 is the app's declared minimum (`minSdk 28`), but its x86_64 Google Play image cannot install this ARM-only APK and the Windows x86_64 emulator cannot boot an ARM64 guest. Android 9 therefore requires a physical ARM64 device. An `INSTALL_FAILED_NO_MATCHING_ABIS` result from the x86_64 API 28 emulator is a laboratory limitation, not an application failure.

## Release policy

1. Keep a new or previously crashing versionCode in runtime quarantine.
2. Let GitHub Actions produce the signed diagnostic artifact without publishing a Release.
3. Download the signed candidate to `C:\AndroidLab\APKs`.
4. Run the emulator matrix and test Android 9 on a physical ARM64 device.
5. Exercise Home, Clips, ordinary video playback, profile, rotation, and background/foreground transitions.
6. Remove quarantine only after the recorded tests pass; the next workflow run may then publish the immutable release.
