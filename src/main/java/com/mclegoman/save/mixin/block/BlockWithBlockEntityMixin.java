/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.block;

import com.mclegoman.save.block.SaveModBlockWithBlockEntity;
import net.minecraft.block.BlockWithBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockWithBlockEntity.class)
public abstract class BlockWithBlockEntityMixin implements SaveModBlockWithBlockEntity {
	@Redirect(method = "onAdded", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockEntity(IIILnet/minecraft/block/entity/BlockEntity;)V"))
	private void save$onAdded(World world, int x, int y, int z, BlockEntity blockEntity) {
		world.setBlockEntity(x, y, z, save$createBlockEntity(x, y, z));
	}
}