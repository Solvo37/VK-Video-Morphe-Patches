package app.vklean.extension.settings;

import android.content.Context;
import android.content.SharedPreferences;

@SuppressWarnings("unused")
public final class VkleanPreferences {
    private static final String FILE = "vklean_settings";
    private static final String KEY_PLAYBACK_SPEED = "playback_speed";
    private static volatile SharedPreferences preferences;

    private VkleanPreferences() {}

    public static void initialize(Context context) {
        if (context == null || preferences != null) return;
        synchronized (VkleanPreferences.class) {
            if (preferences == null) {
                preferences = context.getApplicationContext()
                        .getSharedPreferences(FILE, Context.MODE_PRIVATE);
            }
        }
    }

    public static float playbackSpeed() {
        SharedPreferences prefs = preferences;
        return prefs == null ? 0f : prefs.getFloat(KEY_PLAYBACK_SPEED, 0f);
    }

    public static void setPlaybackSpeed(Context context, float speed) {
        initialize(context);
        SharedPreferences prefs = preferences;
        if (prefs == null) return;
        prefs.edit().putFloat(KEY_PLAYBACK_SPEED, speed).apply();
    }

    public static Float overridePlaybackSpeed(Float original) {
        float speed = playbackSpeed();
        return speed > 0f ? Float.valueOf(speed) : original;
    }
}
