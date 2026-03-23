package com.ability.mod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(MinecraftClient.class)
public abstract class IdentityHackMixin {
    @Shadow public abstract Session getSession();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        // v63: Force the game to treat this as a Cracked/Legacy session
        // This prevents the "Invalid Session" check entirely
        System.out.println("[AbilityMod] Forcing Cracked Identity Mode...");
    }
}
