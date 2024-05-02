package app.revanced.patches.twitch.debug.fingerprints

import app.revanced.patcher.fingerprint.methodFingerprint

internal val shouldShowDebugOptionsFingerprint = methodFingerprint {
    custom { methodDef, _ ->
        methodDef.definingClass.endsWith("/BuildConfigUtil;") && methodDef.name == "shouldShowDebugOptions"
    }
}
