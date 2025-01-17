/*
    mclm_save
    Contributor(s): MCLegoMan
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.fixes;

import com.mclegoman.save.rtu.util.LogType;
import com.mclegoman.save.common.data.Data;
import com.mclegoman.save.config.SaveConfig;
import net.minecraft.client.MinecraftApplet;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.URL;

@ClientOnly
@Mixin(MinecraftApplet.class)
public abstract class MinecraftAppletMixin {
	@Redirect(method = "init", at = @At(value = "INVOKE", target = "Ljava/net/URL;getHost()Ljava/lang/String;"))
	private String mclm_save$setProxy(URL instance) {
		return SaveConfig.instance.proxyUrl.value();
	}
	@Redirect(method = "init", at = @At(value = "INVOKE", target = "Ljava/net/URL;getPort()I"))
	private int mclm_save$setProxyPort(URL instance) {
		return SaveConfig.instance.proxyPort.value();
	}
	@Inject(method = "stop", at = @At(value = "HEAD"))
	private void mclm_save$stop(CallbackInfo ci) {
		if (SaveConfig.instance.logErrorCatching.value()) Data.getVersion().sendToLog(LogType.INFO, "Forcing the game to close.");
		Data.exit(0);
	}
}