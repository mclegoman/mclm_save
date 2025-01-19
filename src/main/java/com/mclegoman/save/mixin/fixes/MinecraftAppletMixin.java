/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.fixes;

import com.mclegoman.save.rtu.util.LogType;
import com.mclegoman.save.data.Data;
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
	private String save$setProxy(URL instance) {
		return SaveConfig.instance.proxyUrl.value();
	}
	@Redirect(method = "init", at = @At(value = "INVOKE", target = "Ljava/net/URL;getPort()I"))
	private int save$setProxyPort(URL instance) {
		return SaveConfig.instance.proxyPort.value();
	}
	@Inject(method = "stop", at = @At(value = "HEAD"))
	private void save$stop(CallbackInfo ci) {
		Data.getVersion().sendToLog(LogType.INFO, "Forcing the game to close.");
		Data.exit(0);
	}
}