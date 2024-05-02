package app.revanced.patches.spotify.navbar

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patches.spotify.navbar.fingerprints.addNavBarItemFingerprint

@Suppress("unused")
val premiumNavbarTabPatch = bytecodePatch(
    name = "Premium navbar tab",
    description = "Hides the premium tab from the navigation bar.",
) {
    compatibleWith("com.spotify.music"())

    dependsOn(premiumNavbarTabResourcePatch)

    val addNavbarItemResult by addNavBarItemFingerprint

    execute {
        addNavbarItemResult.mutableMethod.addInstructions(
            0,
            """
            const v1, $premiumTabId
            if-ne p5, v1, :continue
            return-void
            :continue
            nop
        """,
        )
    }
}
