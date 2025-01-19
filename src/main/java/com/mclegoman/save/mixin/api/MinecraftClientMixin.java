/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.api;

import com.mclegoman.save.rtu.util.LogType;
import com.mclegoman.save.api.entrypoint.SaveModInit;
import com.mclegoman.save.api.event.tick.TickEvents;
import com.mclegoman.save.data.Data;
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
	private void save$init(CallbackInfo ci) {
		System.getProperties().setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		EntrypointUtil.invoke(SaveModInit.key, SaveModInit.class, SaveModInit::init);
	}
	@Inject(method = "m_8832598", at = @At(value = "HEAD"))
	private void save$tick_start(CallbackInfo ci) {
		TickEvents.getStartRegistry().forEach((identifer, tickable) -> {
			try {
				tickable.run((C_5664496) (Object)this);
			} catch (Exception error) {
				Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst ticking " + identifer + " at start tick, removing from registry:" + error.getLocalizedMessage());
				TickEvents.getStartRegistry().remove(identifer);
			}
		});
	}
	@Inject(method = "m_8832598", at = @At(value = "TAIL"))
	private void save$tick_end(CallbackInfo ci) {
		TickEvents.getEndRegistry().forEach((identifer, tickable) -> {
			try {
				tickable.run((C_5664496) (Object)this);
			} catch (Exception error) {
				Data.getVersion().sendToLog(LogType.ERROR, "An error occured whilst ticking " + identifer + " at end tick, removing from registry:" + error.getLocalizedMessage());
				TickEvents.getEndRegistry().remove(identifer);
			}
		});
	}
}
