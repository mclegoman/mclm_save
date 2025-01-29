/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.gui;

import com.mclegoman.save.data.Data;
import com.mclegoman.save.gui.ConfigScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
	@Shadow private Screen parent;
	@Inject(method = "init", at = @At(value = "TAIL"))
	private void save$init(CallbackInfo ci) {
		boolean isDebugLoaded = QuiltLoader.isModLoaded("mclm_debug");
		this.buttons.add(new ButtonWidget(1001, this.width / 2 - 100, this.height / 6 + 110, isDebugLoaded ? 98 : 200, 20, "Save Config"));
	}
	@Inject(method = "buttonClicked", at = @At(value = "TAIL"))
	private void save$buttonClicked(net.minecraft.client.gui.widget.ButtonWidget button, CallbackInfo ci) {
		if (button.active) {
			if (button.id == 1001) {
				Data.Resources.minecraft.m_6408915(new ConfigScreen(new OptionsScreen(this.parent, Data.Resources.minecraft.f_9967940)));
			}
		}
	}
}
