package dev.solvo37.vkvideopatches

import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.resourcePatch
import dev.solvo37.vkvideopatches.Constants.VK_VIDEO
import org.w3c.dom.Element

private const val AMOLED_STYLE = "VKleanAmoledOverlay"

private val AMOLED_ATTRIBUTES = linkedMapOf(
    "vk_legacy_background_content" to "@color/vk_black",
    "vk_legacy_background_page" to "@color/vk_black",
    "vk_legacy_background_light" to "@color/vk_black",
    "vk_legacy_header_background" to "@color/vk_black",
    "vk_legacy_landing_background" to "@color/vk_black",
    "vk_legacy_loader_background" to "@color/vk_black",
    "vk_legacy_search_bar_background" to "@color/vk_black",
    "vk_legacy_segmented_control_bar_background" to "@color/vk_black",
    "vk_legacy_tabbar_background" to "@color/vk_black",
    "vk_legacy_tabbar_tablet_background" to "@color/vk_black",
    "vk_legacy_modal_card_background" to "@color/vk_black",
    "vk_legacy_overlay_status_background" to "@color/vk_black",

    "vk_ui_background" to "#000000",
    "vk_ui_background_content" to "#000000",
    "vk_ui_background_content_alpha" to "#cc000000",
    "vk_ui_background_modal" to "#000000",
    "vk_ui_background_secondary" to "#000000",
    "vk_ui_background_tertiary" to "#000000",
    "vk_ui_header_background" to "#000000",
    "vk_ui_vkontakte_color_loader_background" to "#000000",
    "vk_ui_vkontakte_color_search_bar_background" to "#000000",
    "vk_ui_vkontakte_color_status_background" to "#000000",
    "vk_ui_vkontakte_landing_background" to "#000000",
    "vk_ui_vkontakte_color_tabbar_background" to "#000000",
)

private val amoledThemeResourcesPatch = resourcePatch {
    execute {
        // Refuse a future VK Video build if the dark theme stops exposing the stable
        // background attributes this overlay relies on. This is safer than silently
        // compiling a half-working "AMOLED" switch.
        val available = mutableSetOf<String>()
        document("res/values/styles.xml").use { xml ->
            val items = xml.getElementsByTagName("item")
            for (index in 0 until items.length) {
                val item = items.item(index) as? Element ?: continue
                val name = item.getAttribute("name")
                if (name in AMOLED_ATTRIBUTES) available += name
            }
        }

        val missing = AMOLED_ATTRIBUTES.keys - available
        if (missing.isNotEmpty()) {
            throw PatchException(
                "AMOLED theme: VK dark-theme attributes changed: " +
                    missing.sorted().joinToString()
            )
        }

        val values = get("res").resolve("values")
        if (!values.isDirectory && !values.mkdirs()) {
            throw PatchException("AMOLED theme: could not create res/values")
        }

        val overlay = buildString {
            appendLine("<resources>")
            appendLine("  <style name=\"$AMOLED_STYLE\">")
            appendLine("    <item name=\"android:windowBackground\">@color/vk_black</item>")
            appendLine("    <item name=\"android:colorBackground\">@color/vk_black</item>")
            appendLine("    <item name=\"android:statusBarColor\">@color/vk_black</item>")
            appendLine("    <item name=\"android:navigationBarColor\">@color/vk_black</item>")
            AMOLED_ATTRIBUTES.forEach { (name, value) ->
                appendLine("    <item name=\"$name\">$value</item>")
            }
            appendLine("  </style>")
            appendLine("</resources>")
        }

        values.resolve("vklean_amoled.xml").writeText(overlay)
    }
}

@Suppress("unused")
val amoledThemePatch = bytecodePatch(
    name = "AMOLED theme",
    description = "Adds a VKlean switch that turns VK Video's dark backgrounds, sheets and bars pure black. Light theme is unchanged; changing the switch requires an app restart.",
    default = false
) {
    compatibleWith(VK_VIDEO)
    dependsOn(vkleanSettingsPatch, amoledThemeResourcesPatch)
}
