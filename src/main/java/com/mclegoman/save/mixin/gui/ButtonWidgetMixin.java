/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.gui;

import com.mclegoman.save.util.StringHelper;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ButtonWidget.class)
public abstract class ButtonWidgetMixin {
	@Shadow public String message;
	@Inject(method = "<init>(IIIIILjava/lang/String;)V", at = @At(value = "TAIL"))
	private void save$init(CallbackInfo ci) {
		this.message = StringHelper.getFormattedString(this.message);
	}
}
