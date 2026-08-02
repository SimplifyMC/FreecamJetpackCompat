package com.heyyczer.fjcompat.mixin;

import com.heyyczer.fjcompat.FreecamHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Intercepts the Create Stuff 'N Additions jetpack key.
 *
 * <p>The key is the anonymous class {@code CreateSaModKeyMappings$1}, whose {@code setDown(boolean)}
 * sends the {@code FlyingMessage} packet to the server and triggers the local procedure when
 * pressed. When the player is in freecam controlling the camera, pressing space should only raise
 * the camera; so we cancel {@code setDown} in that moment to keep the jetpack from firing.
 */
@Pseudo
@Mixin(targets = "net.mcreator.createstuffadditions.init.CreateSaModKeyMappings$1", remap = false)
public class JetpackKeyMappingMixin {

    @Inject(
            method = "setDown(Z)V",
            at = @At("HEAD"),
            cancellable = true,
            require = 0,
            expect = 0
    )
    private void freecamjetpackcompat$blockInFreecam(boolean down, CallbackInfo ci) {
        // Only block the key press (down == true); releasing the key follows the normal flow.
        if (down && FreecamHelper.isControlsDisabled()) {
            ci.cancel();
        }
    }
}
