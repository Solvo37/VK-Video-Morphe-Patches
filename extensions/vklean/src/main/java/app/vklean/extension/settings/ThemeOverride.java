package app.vklean.extension.settings;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("unused")
public final class ThemeOverride {
    private static final String STYLE_NAME = "VKleanAmoledOverlay";
    private static final AtomicBoolean INSTALLED = new AtomicBoolean(false);

    private ThemeOverride() {}

    public static boolean isAvailable(Context context) {
        return context != null && styleId(context) != 0;
    }

    public static boolean isDark(Context context) {
        if (context == null) return false;
        int night = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        return night == Configuration.UI_MODE_NIGHT_YES;
    }

    public static boolean isActive(Context context) {
        return isAvailable(context) && isDark(context) && VkleanPreferences.amoledTheme();
    }

    public static void install(Context context) {
        if (context == null) return;
        Context appContext = context.getApplicationContext();
        if (!(appContext instanceof Application)) return;
        if (!INSTALLED.compareAndSet(false, true)) return;

        ((Application) appContext).registerActivityLifecycleCallbacks(
                new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityPreCreated(Activity activity, Bundle state) {
                        if (Build.VERSION.SDK_INT >= 29) {
                            apply(activity);
                        }
                    }

                    @Override public void onActivityCreated(Activity activity, Bundle state) {}
                    @Override public void onActivityStarted(Activity activity) {}
                    @Override public void onActivityResumed(Activity activity) {}
                    @Override public void onActivityPaused(Activity activity) {}
                    @Override public void onActivityStopped(Activity activity) {}
                    @Override public void onActivitySaveInstanceState(Activity activity, Bundle state) {}
                    @Override public void onActivityDestroyed(Activity activity) {}
                }
        );
    }

    public static void apply(Activity activity) {
        if (activity == null || !isActive(activity)) return;

        int style = styleId(activity);
        if (style == 0) return;

        activity.getTheme().applyStyle(style, true);
        if (activity.getWindow() != null) {
            activity.getWindow().setStatusBarColor(0xFF000000);
            activity.getWindow().setNavigationBarColor(0xFF000000);
        }
    }

    private static int styleId(Context context) {
        return context.getResources().getIdentifier(
                STYLE_NAME,
                "style",
                context.getPackageName()
        );
    }
}
