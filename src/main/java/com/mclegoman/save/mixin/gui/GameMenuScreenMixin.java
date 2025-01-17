/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.gui;

import com.mclegoman.save.api.level.SaveModMinecraft;
import com.mclegoman.save.client.gui.screen.world_select.SelectWorldScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
	@Inject(method = "init", at = @At("TAIL"))
	private void save$init(CallbackInfo ci) {
		((ButtonWidget)this.buttons.get(1)).active = false;
		this.buttons.set(2, new ButtonWidget(2, this.width / 2 - 100, this.height / 4 + 48, "Save and quit.."));
	}
	@Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
	private void save$buttonClicked(ButtonWidget buttonWidget, CallbackInfo ci) {
		if (buttonWidget.id == 2) {
			((SaveModMinecraft)this.minecraft).save$exit();
			this.minecraft.m_6408915(null);
			ci.cancel();
		}
		if (buttonWidget.id == 3) {
			this.minecraft.m_6408915(new SelectWorldScreen(this));
			ci.cancel();
		}
	}
}
