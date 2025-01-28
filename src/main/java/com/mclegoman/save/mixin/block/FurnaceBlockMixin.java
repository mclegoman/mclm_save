/*
    Save
    Contributor(s): dannytaylor
    Github: https://github.com/MCLegoMan/mclm_save
    Licence: GNU LGPLv3
*/

package com.mclegoman.save.mixin.block;

import com.mclegoman.save.block.SaveModBlockWithBlockEntity;
import com.mclegoman.save.entity.SaveModBlockEntity;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FurnaceBlock.class)
public abstract class FurnaceBlockMixin implements SaveModBlockWithBlockEntity {
	public BlockEntity save$createBlockEntity(int x, int y, int z) {
		SaveModBlockEntity blockEntity = ((SaveModBlockEntity)new FurnaceBlockEntity());
		blockEntity.save$setX(x);
		blockEntity.save$setY(y);
		blockEntity.save$setZ(z);
		return (BlockEntity) blockEntity;
	}
}