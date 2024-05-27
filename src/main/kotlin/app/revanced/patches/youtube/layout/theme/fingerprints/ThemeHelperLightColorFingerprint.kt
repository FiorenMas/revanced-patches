package app.revanced.patches.youtube.layout.theme.fingerprints

import app.revanced.patcher.fingerprint.methodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal val themeHelperLightColorFingerprint = methodFingerprint {
    accessFlags(AccessFlags.PRIVATE, AccessFlags.STATIC)
    returns("Ljava/lang/String;")
    parameters()
    custom { methodDef, classDef ->
        methodDef.name == "lightThemeResourceName" &&
            classDef.type == "Lapp/revanced/integrations/youtube/ThemeHelper;"
    }
}
