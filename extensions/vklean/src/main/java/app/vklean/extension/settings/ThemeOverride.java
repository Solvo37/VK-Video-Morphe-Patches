package app.vklean.extension.settings;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;

@SuppressWarnings("unused")
public final class ThemeOverride {
    private static final String STYLE_NAME = "VKleanAmoledOverlay";

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

    /**
     * Called from VK's ThemableActivity after VK has chosen and applied its normal theme,
     * but before the concrete screen continues its own onCreate().
     */
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
