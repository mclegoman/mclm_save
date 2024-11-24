/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.mclm_save.mixin.api;

import com.mclegoman.mclm_save.rtu.util.LogType;
import com.mclegoman.mclm_save.api.entrypoint.SaveModInit;
import com.mclegoman.mclm_save.api.event.TickEvents;
import com.mclegoman.mclm_save.common.data.Data;
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
	@Inject(method = "m_8832598", at = @At(value = "HEAD"))
	private void save$tick_start(CallbackInfo ci) {
		for (Runnable runnable : TickEvents.getStartRegistry()) {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, error.getLocalizedMessage());
			}
		}
	}
	@Inject(method = "m_8832598", at = @At(value = "TAIL"))
	private void save$tick_end(CallbackInfo ci) {
		for (Runnable runnable : TickEvents.getEndRegistry()) {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, error.getLocalizedMessage());
			}
		}
	}
}
