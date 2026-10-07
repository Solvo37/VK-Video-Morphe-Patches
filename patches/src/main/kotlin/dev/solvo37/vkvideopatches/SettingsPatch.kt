package dev.solvo37.vkvideopatches

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.resourcePatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import dev.solvo37.vkvideopatches.Constants.VK_VIDEO
import org.w3c.dom.Element

private const val VK_SETTINGS_FRAGMENT =
    "Lcom/vk/video/screens/settings/main/ui/VideoUserSettingsFragment;"
private const val SETTINGS_BRIDGE =
    "Lapp/vklean/extension/settings/SettingsBridge;"
private const val SETTINGS_ACTIVITY =
    "app.vklean.extension.settings.VkleanSettingsActivity"
private const val MAIN_ACTIVITY =
    "Lcom/vk/video/screens/main/MainActivity;"

internal object MainActivityOnCreateFingerprint : Fingerprint(
    definingClass = MAIN_ACTIVITY,
    name = "onCreate",
    returnType = "V",
    parameters = listOf("Landroid/os/Bundle;")
)

internal object VideoUserSettingsOnCreateViewFingerprint : Fingerprint(
    definingClass = VK_SETTINGS_FRAGMENT,
    name = "onCreateView",
    returnType = "Landroid/view/View;",
    parameters = listOf(
        "Landroid/view/LayoutInflater;",
        "Landroid/view/ViewGroup;",
        "Landroid/os/Bundle;"
    )
)

private val vkleanSettingsManifestPatch = resourcePatch {
    execute {
        document("AndroidManifest.xml").use { xml ->
            val application = xml.getElementsByTagName("application").item(0) as? Element
                ?: throw PatchException("VKlean settings: AndroidManifest.xml has no application element.")

            val alreadyDeclared = (0 until xml.getElementsByTagName("activity").length)
                .mapNotNull { xml.getElementsByTagName("activity").item(it) as? Element }
                .any { it.getAttribute("android:name") == SETTINGS_ACTIVITY }

            if (!alreadyDeclared) {
                val activity = xml.createElement("activity")
                activity.setAttribute("android:name", SETTINGS_ACTIVITY)
                activity.setAttribute("android:exported", "false")
                activity.setAttribute("android:label", "VKlean")
                activity.setAttribute(
                    "android:theme",
                    "@android:style/Theme.DeviceDefault.NoActionBar"
                )
                application.appendChild(activity)
            }
        }
    }
}

@Suppress("unused")
val vkleanSettingsPatch = bytecodePatch(
    name = "VKlean settings",
    description = "Adds a VKlean entry to VK Video settings and opens the VKlean settings hub.",
    default = true
) {
    compatibleWith(VK_VIDEO)
    dependsOn(vkleanSettingsManifestPatch)
    extendWith("extensions/vklean.mpe")

    execute {
        MainActivityOnCreateFingerprint.method.addInstructions(
            0,
            """
                invoke-static/range {p0 .. p0}, $SETTINGS_BRIDGE->initialize(Landroid/content/Context;)V
            """
        )

        VideoUserSettingsOnCreateViewFingerprint.method.apply {
            val implementation = implementation
                ?: throw PatchException("VKlean settings: settings onCreateView has no implementation.")

            val returnIndex = implementation.instructions.indexOfLast {
                it.opcode == Opcode.RETURN_OBJECT
            }
            if (returnIndex < 0) {
                throw PatchException("VKlean settings: settings onCreateView has no return-object.")
            }

            val returnRegister = getInstruction<OneRegisterInstruction>(returnIndex).registerA

            addInstructions(
                returnIndex,
                """
                    invoke-static {v$returnRegister, p0}, $SETTINGS_BRIDGE->wrap(Landroid/view/View;Ljava/lang/Object;)Landroid/view/View;
                    move-result-object v$returnRegister
                """
            )
        }
    }
}
