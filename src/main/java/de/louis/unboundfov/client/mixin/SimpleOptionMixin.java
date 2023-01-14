package de.louis.unboundfov.client.mixin;

import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(SimpleOption.class)
public class SimpleOptionMixin<T> {
    @Shadow T value;

    @Shadow private Consumer<T> changeCallback;

    @Inject(method = "setValue",at = @At("HEAD"),cancellable = true)
    private void acceptAnything(T value, CallbackInfo ci) {
        this.value = value;
        changeCallback.accept(value);
        ci.cancel();
    }
}
