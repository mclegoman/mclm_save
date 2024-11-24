package com.mclegoman.mclm_save.mixin.api;

import com.mclegoman.luminance.common.util.LogType;
import com.mclegoman.mclm_save.api.entrypoint.SaveModInit;
import com.mclegoman.mclm_save.api.event.TickEvents;
import com.mclegoman.mclm_save.client.util.Accessors;
import com.mclegoman.mclm_save.common.data.Data;
import net.minecraft.client.C_5664496;
import net.minecraft.world.World;
import org.quiltmc.loader.api.entrypoint.EntrypointUtil;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@ClientOnly
@Mixin(C_5664496.class)
public abstract class MinecraftClientMixin {
	@Inject(method = "run", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GameGui;<init>(Lnet/minecraft/client/C_5664496;)V"))
	private void mclm_save$init(CallbackInfo ci) {
		EntrypointUtil.invoke(SaveModInit.key, SaveModInit.class, SaveModInit::init);
	}
	@Shadow
	public Canvas f_0769488;
	@Shadow public abstract void m_9890357(World world);

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
		// Updates Canvas Accessor and checks if the world needs to load.
		Accessors.MinecraftClient.canvas = this.f_0769488;
		if (Accessors.MinecraftClient.shouldLoad && Accessors.MinecraftClient.world != null) {
			Accessors.MinecraftClient.shouldLoad = false;
			this.m_9890357(Accessors.MinecraftClient.world);
		}
		for (Runnable runnable : TickEvents.getEndRegistry()) {
			try {
				runnable.run();
			} catch (Exception error) {
				Data.version.sendToLog(LogType.ERROR, error.getLocalizedMessage());
			}
		}
	}
}
