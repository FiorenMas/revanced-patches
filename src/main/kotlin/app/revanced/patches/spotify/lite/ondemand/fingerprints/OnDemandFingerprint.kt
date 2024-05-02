package app.revanced.patches.spotify.lite.ondemand.fingerprints

import app.revanced.patcher.fingerprint.methodFingerprint
import com.android.tools.smali.dexlib2.Opcode

internal val onDemandFingerprint = methodFingerprint(fuzzyPatternScanThreshold = 2) {
    returns("L")
    parameters()
    opcodes(
        Opcode.INVOKE_STATIC,
        Opcode.MOVE_RESULT,
        Opcode.INVOKE_STATIC,
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.IF_EQZ,
        Opcode.SGET_OBJECT,
        Opcode.GOTO,
        Opcode.SGET_OBJECT,
        Opcode.INVOKE_VIRTUAL,
        Opcode.MOVE_RESULT,
        Opcode.IPUT,
        Opcode.RETURN_OBJECT,
    )
}
