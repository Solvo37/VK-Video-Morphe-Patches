package dev.solvo37.vkvideopatches

import app.morphe.patcher.Fingerprint
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.extensions.InstructionExtensions.getInstruction
import app.morphe.patcher.patch.PatchException
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import dev.solvo37.vkvideopatches.Constants.VK_VIDEO

private const val PLAYBACK_SPEED_REPOSITORY = "Lv23/z;"
private const val VKLEAN_PREFERENCES =
    "Lapp/vklean/extension/settings/VkleanPreferences;"

internal object PlaybackSpeedRepositoryGetterFingerprint : Fingerprint(
    returnType = "Ljava/lang/Float;",
    parameters = emptyList(),
    custom = { _, classDef ->
        PLAYBACK_SPEED_REPOSITORY in classDef.interfaces &&
            classDef.methods.any { method ->
                method.returnType == "V" &&
                    method.parameterTypes == listOf("Ljava/lang/Float;")
            }
    }
)

@Suppress("unused")
val playbackSpeedPatch = bytecodePatch(
    name = "Playback speed",
    description = "Adds a persistent playback-speed choice in VKlean settings while keeping VK Video's native speed controls.",
    default = true
) {
    compatibleWith(VK_VIDEO)
    dependsOn(vkleanSettingsPatch)

    execute {
        PlaybackSpeedRepositoryGetterFingerprint.method.apply {
            val implementation = implementation
                ?: throw PatchException("Playback speed: repository getter has no implementation.")

            val returnIndexes = implementation.instructions
                .mapIndexedNotNull { index, instruction ->
                    index.takeIf { instruction.opcode == Opcode.RETURN_OBJECT }
                }

            if (returnIndexes.isEmpty()) {
                throw PatchException("Playback speed: repository getter has no return-object.")
            }

            returnIndexes.asReversed().forEach { returnIndex ->
                val register = getInstruction<OneRegisterInstruction>(returnIndex).registerA
                addInstructions(
                    returnIndex,
                    """
                        invoke-static/range {v$register .. v$register}, $VKLEAN_PREFERENCES->overridePlaybackSpeed(Ljava/lang/Float;)Ljava/lang/Float;
                        move-result-object v$register
                    """
                )
            }
        }
    }
}
