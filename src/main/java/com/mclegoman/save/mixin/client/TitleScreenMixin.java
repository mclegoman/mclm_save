/*
    mclm_save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.client;

import com.mclegoman.save.client.april_fools.AprilFools;
import net.minecraft.client.C_8938952;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(C_8938952.class)
public abstract class TitleScreenMixin extends Screen {
	@Shadow private String f_4536664;
	@Inject(method = "<init>", at = @At("RETURN"))
	private void save$replaceSplashText(CallbackInfo ci) {
		// If it's April 1st, or Force April Fools is enabled, the splash text is replaced with Terraria 3,
		// unless the config has the message replaced.
		if (AprilFools.isAprilFools()) this.f_4536664 = AprilFools.getVersionString(this.f_4536664);
	}
	@Inject(method = "init", at = @At("TAIL"))
	private void save$init(CallbackInfo ci) {
		// Disables the Generate new level... button,
		// as we're going to replace the load world with a select world, plus no save screen as it's gonna auto save.
		((ButtonWidget)this.buttons.get(0)).active = false;
	}
}
