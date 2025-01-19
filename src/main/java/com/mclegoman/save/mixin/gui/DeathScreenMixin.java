/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.gui;

import com.mclegoman.save.level.SaveModMinecraft;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(DeathScreen.class)
public abstract class DeathScreenMixin extends Screen {
	@Inject(method = "init", at = @At("TAIL"))
	private void save$init(CallbackInfo ci) {
		this.buttons.set(0, new ButtonWidget(1, this.width / 2 - 100, this.height / 4 + 72, "Respawn"));
		this.buttons.set(1, new ButtonWidget(2, this.width / 2 - 100, this.height / 4 + 96, "Save and quit.."));
		if (this.minecraft.f_2424468 == null) ((ButtonWidget)this.buttons.get(1)).active = false;
	}
	@Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
	private void save$buttonClicked(ButtonWidget buttonWidget, CallbackInfo ci) {
		if (buttonWidget.id == 1) {
			buttonWidget.active = false;
			((SaveModMinecraft)this.minecraft).save$respawn();
			this.minecraft.m_6408915(null);
		}
		if (buttonWidget.id == 2) {
			((SaveModMinecraft)this.minecraft).save$exit();
			this.minecraft.m_6408915(null);
		}
		ci.cancel();
	}
}
