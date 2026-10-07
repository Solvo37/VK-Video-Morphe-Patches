package app.vklean.extension.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressWarnings("unused")
public final class SettingsBridge {
    private SettingsBridge() {}

    public static void initialize(Context context) {
        VkleanPreferences.initialize(context);
    }

    public static View wrap(View content, Object hostFragment) {
        initialize(content == null ? null : content.getContext());
        if (content == null) return null;

        final Context context = content.getContext();
        final boolean dark = ThemeOverride.isDark(context);
        final boolean amoled = ThemeOverride.isActive(context);

        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        root.setBackgroundColor(amoled ? 0xFF000000 : (dark ? 0xFF19191A : 0xFFFFFFFF));

        LinearLayout entry = new LinearLayout(context);
        entry.setOrientation(LinearLayout.VERTICAL);
        entry.setGravity(Gravity.CENTER_VERTICAL);
        entry.setPadding(dp(context, 20), dp(context, 10), dp(context, 20), dp(context, 10));
        entry.setClickable(true);
        entry.setFocusable(true);
        entry.setContentDescription("VKlean settings");

        TextView title = new TextView(context);
        title.setText("VKlean");
        title.setTextSize(16f);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setTextColor(dark ? 0xFFFFFFFF : 0xFF111111);

        TextView subtitle = new TextView(context);
        subtitle.setText("Дополнительные настройки");
        subtitle.setTextSize(13f);
        subtitle.setTextColor(dark ? 0xFF9E9EA3 : 0xFF6D6D72);

        entry.addView(title, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        LinearLayout.LayoutParams subtitleParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        subtitleParams.topMargin = dp(context, 2);
        entry.addView(subtitle, subtitleParams);

        entry.setOnClickListener(view -> open(context));

        root.addView(entry, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(context, 68)
        ));

        View divider = new View(context);
        divider.setBackgroundColor(amoled ? 0xFF202020 : (dark ? 0xFF2C2C2E : 0xFFE8E8EA));
        root.addView(divider, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dp(context, 1)
        ));

        // The original VK settings screen is a ComposeView with MATCH_PARENT height.
        // Give it the remaining space instead of letting it cover our entry.
        LinearLayout.LayoutParams contentParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1f
        );
        root.addView(content, contentParams);

        return root;
    }

    private static void open(Context context) {
        Intent intent = new Intent(context, VkleanSettingsActivity.class);
        intent.putExtra(VkleanSettingsActivity.EXTRA_HOST_DARK, ThemeOverride.isDark(context));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    static int dp(Context context, int value) {
        return Math.round(value * context.getResources().getDisplayMetrics().density);
    }
}
