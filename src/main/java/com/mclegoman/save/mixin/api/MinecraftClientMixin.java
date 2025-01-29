/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.api;

import com.mclegoman.save.api.entrypoint.SaveModInit;
import com.mclegoman.save.api.event.Execute;
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
	@Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GameGui;<init>(Lnet/minecraft/client/C_5664496;II)V"))
	private void save$init(CallbackInfo ci) {
		System.getProperties().setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		EntrypointUtil.invoke(SaveModInit.key, SaveModInit.class, SaveModInit::init);
	}
	@Inject(method = "m_8832598", at = @At(value = "HEAD"))
	private void save$tick_start(CallbackInfo ci) {
		Execute.Tick.start((C_5664496) (Object) this);
	}
	@Inject(method = "m_8832598", at = @At(value = "TAIL"))
	private void save$tick_end(CallbackInfo ci) {
		Execute.Tick.end((C_5664496) (Object) this);
	}
}
