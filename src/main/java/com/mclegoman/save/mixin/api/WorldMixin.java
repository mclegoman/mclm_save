/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.api;

import com.mclegoman.save.api.event.Execute;
import com.mclegoman.save.data.Data;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public abstract class WorldMixin {
	@Inject(method = "tick", at = @At("HEAD"))
	private void save$tick_startWorld(CallbackInfo ci) {
		Execute.Tick.startWorld(Data.Resources.minecraft, (World) (Object) this);
	}
	@Inject(method = "tick", at = @At("TAIL"))
	private void save$tick_endWorld(CallbackInfo ci) {
		Execute.Tick.endWorld(Data.Resources.minecraft, (World) (Object) this);
	}
}
