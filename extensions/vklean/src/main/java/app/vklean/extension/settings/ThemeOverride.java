package app.vklean.extension.settings;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.TypedValue;

@SuppressWarnings("unused")
public final class ThemeOverride {
    private static final String STYLE_NAME = "VKleanAmoledOverlay";
    private static final String BACKGROUND_ATTR = "vk_ui_background_content";

    private ThemeOverride() {}

    public static boolean isAvailable(Context context) {
        return context != null && styleId(context) != 0;
    }

    /**
     * Reads VK's actual background attribute after VKTheme has been applied.
     * This follows VK Video's own light/dark choice even when it differs from
     * the phone-wide night mode.
     */
    public static boolean isDark(Context context) {
        if (context == null) return false;

        int attr = context.getResources().getIdentifier(
                BACKGROUND_ATTR,
                "attr",
                context.getPackageName()
        );
        if (attr != 0) {
            TypedValue value = new TypedValue();
            if (context.getTheme() != null
                    && context.getTheme().resolveAttribute(attr, value, true)) {
                Integer color = resolvedColor(context, value);
                if (color != null) {
                    int red = Color.red(color);
                    int green = Color.green(color);
                    int blue = Color.blue(color);
                    // VK light backgrounds are near white, its dark schemes are
                    // well below this threshold (#19191a in 1.165).
                    return Math.max(red, Math.max(green, blue)) < 0x80;
                }
            }
        }

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

    private static Integer resolvedColor(Context context, TypedValue value) {
        if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT
                && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
            return value.data;
        }
        if (value.resourceId != 0) {
            try {
                return context.getColor(value.resourceId);
            } catch (RuntimeException ignored) {
                return null;
            }
        }
        return null;
    }

    private static int styleId(Context context) {
        return context.getResources().getIdentifier(
                STYLE_NAME,
                "style",
                context.getPackageName()
        );
    }
}
