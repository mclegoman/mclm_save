/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.api;

import com.mclegoman.save.data.Data;
import net.minecraft.client.C_5664496;
import net.minecraft.client.C_9029783;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(C_9029783.class)
public abstract class ResourcesMixin {
	@Shadow private C_5664496 f_6145320;
	@Inject(method = "<init>", at = @At(value = "TAIL"))
	private void save$init(CallbackInfo ci) {
		Data.Resources.minecraft = this.f_6145320;
	}
}
