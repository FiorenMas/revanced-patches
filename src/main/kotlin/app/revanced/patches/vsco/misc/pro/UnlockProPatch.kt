package app.revanced.patches.vsco.misc.pro

import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patches.vsco.misc.pro.fingerprints.revCatSubscriptionFingerprint

@Suppress("unused")
val unlockProPatch = bytecodePatch(
    name = "Unlock pro",
    description = "Unlocks pro features.",
) {
    compatibleWith("com.vsco.cam"("345"))

    val revCatSubscriptionResult by revCatSubscriptionFingerprint

    execute {
        revCatSubscriptionResult.mutableMethod.addInstruction(
            0,
            """
                const p1, 0x1
            """,
        )
    }
}
