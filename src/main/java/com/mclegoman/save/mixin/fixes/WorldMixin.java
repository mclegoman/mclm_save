/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.fixes;

import com.mclegoman.save.config.SaveConfig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.world.World;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(World.class)
public abstract class WorldMixin {
	@Inject(method = "addEntity", at = @At(value = "HEAD"), cancellable = true)
	private void save$preventFlowerLag(Entity entity, CallbackInfo ci) {
		if (SaveConfig.instance.shouldDisableFlowerItems.value()) {
			if (entity instanceof ItemEntity) {
				for (String value : SaveConfig.instance.flowerItems.value()) {
					if (Integer.parseInt(value) == ((ItemEntity) entity).item.itemId) ci.cancel();
				}
			}
		}
	}
}