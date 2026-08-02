package com.heyyczer.fjcompat;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;

import java.lang.reflect.Method;

/**
 * Helper for querying the Freecam mod's state.
 *
 * <p>The Freecam API ({@code net.xolt.freecam.Freecam}) is accessed through reflection rather
 * than a direct reference. This keeps Freecam as an optional dependency (the mod still works
 * when Freecam is not installed) and avoids coupling compilation to the Java version used to
 * compile the Freecam jar.
 */
public final class FreecamHelper {

    private static final Logger LOGGER = LogUtils.getLogger();

    private static Method isEnabledMethod;
    private static Method isPlayerControlEnabledMethod;
    private static boolean reflectionReady;

    static {
        if (ModList.get().isLoaded("freecam")) {
            try {
                Class<?> freecam = Class.forName("net.xolt.freecam.Freecam");
                isEnabledMethod = freecam.getMethod("isEnabled");
                isPlayerControlEnabledMethod = freecam.getMethod("isPlayerControlEnabled");
                reflectionReady = true;
            } catch (ReflectiveOperationException e) {
                LOGGER.warn("Could not access the Freecam API; jetpack compatibility will be inactive.", e);
            }
        }
    }

    /**
     * @return {@code true} are controls blocked, i.e. when Freecam is active and
     * controlling the camera (not the player).
     */
    public static boolean isControlsDisabled() {
        if (!reflectionReady) {
            return false;
        }
        try {
            boolean enabled = (boolean) isEnabledMethod.invoke(null);
            // When "player control" is on, the keys move the player (not the camera),
            // so the jetpack should keep working normally.
            boolean playerControl = (boolean) isPlayerControlEnabledMethod.invoke(null);
            return enabled && !playerControl;
        } catch (ReflectiveOperationException e) {
            return false;
        }
    }
}
