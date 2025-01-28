/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.api;

import com.mclegoman.save.api.event.Execute;
import net.minecraft.client.C_5664496;
import net.minecraft.client.render.GameRenderer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
	@Shadow private C_5664496 minecraft;
	@Inject(method = "m_8576613", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GameGui;render(F)V", shift = At.Shift.AFTER))
	private void save$render_afterGameGui(float f, CallbackInfo ci) {
		Execute.Render.afterGameGui(this.minecraft);
	}
	@Inject(method = "m_8576613", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;yield()V"))
	private void save$render_end(float f, CallbackInfo ci) {
		Execute.Render.end(this.minecraft);
	}
}
