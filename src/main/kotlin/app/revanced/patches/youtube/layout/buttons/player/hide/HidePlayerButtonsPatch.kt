package app.revanced.patches.youtube.layout.buttons.player.hide

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patches.all.misc.resources.addResources
import app.revanced.patches.all.misc.resources.addResourcesPatch
import app.revanced.patches.shared.misc.settings.preference.SwitchPreference
import app.revanced.patches.youtube.layout.buttons.player.hide.fingerprints.playerControlsVisibilityModelFingerprint
import app.revanced.patches.youtube.misc.integrations.integrationsPatch
import app.revanced.patches.youtube.misc.settings.PreferenceScreen
import app.revanced.patches.youtube.misc.settings.settingsPatch
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction3rc

private object ParameterOffsets {
    const val HAS_NEXT = 5
    const val HAS_PREVIOUS = 6
}

@Suppress("unused")
val hidePlayerButtonsPatch = bytecodePatch(
    name = "Hide player buttons",
    description = "Adds an option to hide the previous and next buttons in the video player.",
) {
    compatibleWith(
        "com.google.android.youtube"(
            "18.32.39",
            "18.37.36",
            "18.38.44",
            "18.43.45",
            "18.44.41",
            "18.45.43",
            "18.48.39",
            "18.49.37",
            "19.01.34",
            "19.02.39",
            "19.03.36",
            "19.04.38",
            "19.05.36",
            "19.06.39",
            "19.07.40",
            "19.08.36",
            "19.09.38",
            "19.10.39",
            "19.11.43",
        ),
    )

    dependsOn(integrationsPatch, settingsPatch, addResourcesPatch)

    val playerControlsVisibilityModelResult by playerControlsVisibilityModelFingerprint

    execute {
        addResources(this)

        PreferenceScreen.PLAYER.addPreferences(
            SwitchPreference("revanced_hide_player_buttons"),
        )

        playerControlsVisibilityModelResult.apply {
            val callIndex = scanResult.patternScanResult!!.endIndex
            val callInstruction = mutableMethod.getInstruction<Instruction3rc>(callIndex)

            // overriding this parameter register hides the previous and next buttons
            val hasNextParameterRegister = callInstruction.startRegister + ParameterOffsets.HAS_NEXT
            val hasPreviousParameterRegister = callInstruction.startRegister + ParameterOffsets.HAS_PREVIOUS

            mutableMethod.addInstructions(
                callIndex,
                """
                    invoke-static { v$hasNextParameterRegister }, Lapp/revanced/integrations/youtube/patches/HidePlayerButtonsPatch;->previousOrNextButtonIsVisible(Z)Z
                    move-result v$hasNextParameterRegister
                    
                    invoke-static { v$hasPreviousParameterRegister }, Lapp/revanced/integrations/youtube/patches/HidePlayerButtonsPatch;->previousOrNextButtonIsVisible(Z)Z
                    move-result v$hasPreviousParameterRegister
                """,
            )
        }
    }
}
