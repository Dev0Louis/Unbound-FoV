package de.louis.unboundfov.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;

@Mixin(GameOptions.class)
public class GameOptionsMixin {

    @Shadow @Final private SimpleOption<Integer> fov;

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    public void test(MinecraftClient client, File optionsFile, CallbackInfo ci) {
        this.fov.callbacks = new SimpleOption.ValidatingIntSliderCallbacks(1, 360);
    }

    @Inject(
            method = "getFov",
            at = @At(value = "HEAD")
    )
    public void fix0And360Crash(CallbackInfoReturnable<SimpleOption<Integer>> cir) {
        if(this.fov.getValue() == 1) {
            this.fov.setValue(2);
        } else if (this.fov.getValue() == 360) {
            this.fov.setValue(359);
        }
    }
}
