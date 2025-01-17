/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.client;

import com.mclegoman.save.client.april_fools.AprilFools;
import net.minecraft.client.C_5664496;
import net.minecraft.client.gui.GameGui;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@ClientOnly
@Mixin(GameGui.class)
public abstract class GameGuiMixin {
	@Shadow private C_5664496 minecraft;
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;drawWithShadow(Ljava/lang/String;III)V", ordinal = 4))
	private String save$version(String text) {
		return AprilFools.getVersionString(text);
	}
	@ModifyArg(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TextRenderer;drawWithShadow(Ljava/lang/String;III)V", ordinal = 0))
	private String save$version_showfps(String text) {
		return AprilFools.getVersionString(text) + " (" + this.minecraft.f_0489718 + ")";
	}
}
