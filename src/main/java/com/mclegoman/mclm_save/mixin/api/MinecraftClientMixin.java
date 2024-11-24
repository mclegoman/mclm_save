package com.mclegoman.mclm_save.mixin.api;

import com.mclegoman.mclm_save.api.entrypoint.SaveModInit;
import net.minecraft.client.C_5664496;
import org.quiltmc.loader.api.entrypoint.EntrypointUtil;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(C_5664496.class)
public abstract class MinecraftClientMixin {
	@Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GameGui;<init>(Lnet/minecraft/client/C_5664496;)V"))
	private void mclm_save$init(CallbackInfo ci) {
		EntrypointUtil.invoke(SaveModInit.key, SaveModInit.class, SaveModInit::init);
	}
}
