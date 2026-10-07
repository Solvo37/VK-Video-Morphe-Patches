package app.vklean.extension.settings;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public final class VkleanSettingsActivity extends Activity {
    private static final String EXTRA_SECTION = "vklean_section";

    private boolean dark;
    private int background;
    private int card;
    private int primary;
    private int secondary;
    private int accent;
    private int divider;

    private static final Map<String, String[]> SECTIONS = new LinkedHashMap<>();

    static {
        SECTIONS.put("Воспроизведение", new String[]{
                "Скорость воспроизведения",
                "Качество видео"
        });
        SECTIONS.put("Клипы и лента", new String[]{
                "Скрытие интерфейса Клипов",
                "Автолисталка Клипов",
                "Фильтр ленты",
                "Не показывать просмотренное",
                "Кнопка «Не интересно»",
                "Всегда показывать дату публикации"
        });
        SECTIONS.put("Загрузки", new String[]{
                "Расширенные загрузки"
        });
        SECTIONS.put("Внешний вид", new String[]{
                "AMOLED-тема",
                "Значок приложения"
        });
        SECTIONS.put("Приватность", new String[]{
                "Отключение телеметрии"
        });
    }

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        VkleanPreferences.initialize(this);
        configureTheme();

        String section = getIntent().getStringExtra(EXTRA_SECTION);
        setContentView(section == null ? buildMain() : buildSection(section));
    }

    private void configureTheme() {
        dark = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
        background = dark ? 0xFF111112 : 0xFFF4F4F6;
        card = dark ? 0xFF1D1D1F : 0xFFFFFFFF;
        primary = dark ? 0xFFF5F5F7 : 0xFF111114;
        secondary = dark ? 0xFFA8A8AD : 0xFF6D6D72;
        accent = 0xFF2688EB;
        divider = dark ? 0xFF303033 : 0xFFE7E7EA;

        Window window = getWindow();
        window.setStatusBarColor(background);
        window.setNavigationBarColor(background);
        if (!dark && android.os.Build.VERSION.SDK_INT >= 23) {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                            | (android.os.Build.VERSION.SDK_INT >= 26
                            ? View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR : 0)
            );
        }
    }

    private View buildMain() {
        LinearLayout page = page();

        TextView title = heading("VKlean");
        page.addView(title);

        TextView subtitle = text(
                "Дополнительные настройки VK Видео",
                14f,
                secondary,
                Typeface.NORMAL
        );
        LinearLayout.LayoutParams subParams = matchWrap();
        subParams.topMargin = dp(4);
        subParams.bottomMargin = dp(18);
        page.addView(subtitle, subParams);

        for (String section : SECTIONS.keySet()) {
            page.addView(menuRow(section, sectionSummary(section), () -> openSection(section)));
        }

        TextView note = text(
                "Это каркас VKlean Settings. Реальные переключатели будут появляться здесь по мере добавления патчей.",
                13f,
                secondary,
                Typeface.NORMAL
        );
        LinearLayout.LayoutParams noteParams = matchWrap();
        noteParams.topMargin = dp(18);
        noteParams.bottomMargin = dp(24);
        page.addView(note, noteParams);

        return scroll(page);
    }

    private View buildSection(String section) {
        LinearLayout page = page();

        TextView back = text("‹  Назад", 16f, accent, Typeface.BOLD);
        back.setGravity(Gravity.CENTER_VERTICAL);
        back.setClickable(true);
        back.setFocusable(true);
        back.setOnClickListener(v -> finish());
        LinearLayout.LayoutParams backParams = matchWrap();
        backParams.bottomMargin = dp(16);
        page.addView(back, backParams);

        page.addView(heading(section));

        String[] entries = SECTIONS.get(section);
        if (entries == null) entries = new String[]{"Раздел в разработке"};

        LinearLayout cardView = new LinearLayout(this);
        cardView.setOrientation(LinearLayout.VERTICAL);
        cardView.setBackground(rounded(card, 16));
        LinearLayout.LayoutParams cardParams = matchWrap();
        cardParams.topMargin = dp(16);
        page.addView(cardView, cardParams);

        for (int i = 0; i < entries.length; i++) {
            if ("Воспроизведение".equals(section)
                    && "Скорость воспроизведения".equals(entries[i])) {
                cardView.addView(playbackSpeedRow());
            } else {
                cardView.addView(plannedRow(entries[i]));
            }
            if (i != entries.length - 1) {
                View line = new View(this);
                line.setBackgroundColor(divider);
                LinearLayout.LayoutParams lineParams = new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        dp(1)
                );
                lineParams.leftMargin = dp(16);
                cardView.addView(line, lineParams);
            }
        }

        TextView note = text(
                "Пункты пока только размечены. Следующим шагом каждый из них получит настоящий runtime-переключатель или выбор значения.",
                13f,
                secondary,
                Typeface.NORMAL
        );
        LinearLayout.LayoutParams noteParams = matchWrap();
        noteParams.topMargin = dp(16);
        noteParams.bottomMargin = dp(24);
        page.addView(note, noteParams);

        return scroll(page);
    }

    private LinearLayout page() {
        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(16), dp(18), dp(16), dp(24));
        page.setBackgroundColor(background);
        return page;
    }

    private ScrollView scroll(View child) {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setBackgroundColor(background);
        scroll.addView(child, new ScrollView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return scroll;
    }

    private TextView heading(String value) {
        return text(value, 28f, primary, Typeface.BOLD);
    }

    private View menuRow(String title, String summary, Runnable click) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.VERTICAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(16), dp(13), dp(16), dp(13));
        row.setMinimumHeight(dp(72));
        row.setBackground(rounded(card, 16));
        row.setClickable(true);
        row.setFocusable(true);
        row.setOnClickListener(v -> click.run());

        TextView name = text(title + "   ›", 16f, primary, Typeface.BOLD);
        row.addView(name, matchWrap());

        TextView details = text(summary, 13f, secondary, Typeface.NORMAL);
        LinearLayout.LayoutParams detailsParams = matchWrap();
        detailsParams.topMargin = dp(3);
        row.addView(details, detailsParams);

        LinearLayout.LayoutParams params = matchWrap();
        params.bottomMargin = dp(10);
        row.setLayoutParams(params);
        return row;
    }

    private View playbackSpeedRow() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.VERTICAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(16), dp(12), dp(12), dp(12));
        row.setMinimumHeight(dp(64));
        row.setClickable(true);
        row.setFocusable(true);
        row.setOnClickListener(v -> showPlaybackSpeedDialog());

        TextView name = text("Скорость воспроизведения", 15f, primary, Typeface.NORMAL);
        row.addView(name, matchWrap());

        TextView value = text(
                playbackSpeedLabel(VkleanPreferences.playbackSpeed()),
                13f,
                accent,
                Typeface.BOLD
        );
        LinearLayout.LayoutParams valueParams = matchWrap();
        valueParams.topMargin = dp(4);
        row.addView(value, valueParams);

        return row;
    }

    private void showPlaybackSpeedDialog() {
        final String[] labels = new String[]{
                "Как в VK Видео",
                "0.5×",
                "0.75×",
                "1×",
                "1.25×",
                "1.5×",
                "1.75×",
                "2×"
        };
        final float[] values = new float[]{
                0f, 0.5f, 0.75f, 1f, 1.25f, 1.5f, 1.75f, 2f
        };

        float current = VkleanPreferences.playbackSpeed();
        int selected = 0;
        for (int i = 0; i < values.length; i++) {
            if (Math.abs(values[i] - current) < 0.001f) {
                selected = i;
                break;
            }
        }

        new android.app.AlertDialog.Builder(this)
                .setTitle("Скорость воспроизведения")
                .setSingleChoiceItems(labels, selected, (dialog, which) -> {
                    VkleanPreferences.setPlaybackSpeed(this, values[which]);
                    dialog.dismiss();
                    recreate();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private String playbackSpeedLabel(float value) {
        if (value <= 0f) return "Как в VK Видео";
        if (Math.abs(value - Math.round(value)) < 0.001f) {
            return ((int) value) + "×";
        }
        String raw = Float.toString(value);
        return raw + "×";
    }

    private View plannedRow(String title) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(16), dp(13), dp(12), dp(13));
        row.setMinimumHeight(dp(56));

        TextView name = text(title, 15f, primary, Typeface.NORMAL);
        row.addView(name, new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
        ));

        TextView badge = text("Скоро", 12f, secondary, Typeface.BOLD);
        badge.setGravity(Gravity.CENTER);
        badge.setPadding(dp(9), dp(4), dp(9), dp(4));
        badge.setBackground(rounded(dark ? 0xFF2A2A2D : 0xFFF0F0F2, 20));
        row.addView(badge, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));

        return row;
    }

    private void openSection(String section) {
        Intent intent = new Intent(this, VkleanSettingsActivity.class);
        intent.putExtra(EXTRA_SECTION, section);
        startActivity(intent);
    }

    private String sectionSummary(String section) {
        if ("Воспроизведение".equals(section)) return "Скорость и качество видео";
        if ("Клипы и лента".equals(section)) return "Интерфейс, фильтры, просмотренное и автолисталка";
        if ("Загрузки".equals(section)) return "Качество и расширенные варианты сохранения";
        if ("Внешний вид".equals(section)) return "AMOLED и смена значка VK Видео";
        if ("Приватность".equals(section)) return "Телеметрия и связанные ограничения";
        return "";
    }

    private TextView text(String value, float size, int color, int style) {
        TextView view = new TextView(this);
        view.setText(value);
        view.setTextSize(size);
        view.setTextColor(color);
        view.setTypeface(Typeface.DEFAULT, style);
        view.setIncludeFontPadding(false);
        return view;
    }

    private GradientDrawable rounded(int color, int radiusDp) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(dp(radiusDp));
        return shape;
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
    }

    private int dp(int value) {
        return SettingsBridge.dp(this, value);
    }
}
