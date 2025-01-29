/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.fixes.resize;

import com.mclegoman.save.rtu.util.LogType;
import com.mclegoman.save.data.Data;
import com.mclegoman.save.util.Resize;
import net.minecraft.client.gui.GameGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameGui.class)
public abstract class GameGuiMixin {
	@Shadow private int f_0775750;
	@Shadow private int f_8519063;
	@Inject(method = "render", at = @At(value = "HEAD"))
	private void save$updateSize(CallbackInfo ci) {
		try {
			if (Resize.shouldResize) {
				this.f_0775750 = Data.Resources.minecraft.f_0545414 * 240 / Data.Resources.minecraft.f_5990000;
				this.f_8519063 = Data.Resources.minecraft.f_5990000 * 240 / Data.Resources.minecraft.f_5990000;
				Resize.shouldResize = false;
			}
		} catch (Exception error) {
			Data.getVersion().sendToLog(LogType.WARN, "Error updating gamegui size: " + error.getLocalizedMessage());
		}
	}
}